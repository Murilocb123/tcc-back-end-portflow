DROP VIEW IF EXISTS vw_portfolio_daily_returns CASCADE;

CREATE OR REPLACE VIEW vw_portfolio_daily_returns AS
WITH
    biz_dates AS (
        SELECT DISTINCT ah.price_date::date AS dt
        FROM asset_history ah
        WHERE ah.price_date <= current_date
    ),
    transactions_aligned AS (   -- igual ao seu
        SELECT
            t.tenant,
            t.portfolio,
            t.asset,
            (
                SELECT bd.dt
                FROM biz_dates bd
                WHERE bd.dt >= t.trade_date::date
                ORDER BY bd.dt
                LIMIT 1
            ) AS dt,
            CASE
                WHEN t.quantity IS NULL THEN 0
                WHEN COALESCE(UPPER(t.type),'') = 'BUY'  THEN ABS(t.quantity)
                WHEN COALESCE(UPPER(t.type),'') = 'SELL' THEN -ABS(t.quantity)
                ELSE t.quantity
                END AS delta_qty
        FROM transaction t
        WHERE t.trade_date <= current_date
    ),
    position_deltas_agg AS (
        SELECT
            ta.tenant, ta.portfolio, ta.asset, ta.dt,
            SUM(ta.delta_qty) AS delta_qty
        FROM transactions_aligned ta
        WHERE ta.dt IS NOT NULL
        GROUP BY ta.tenant, ta.portfolio, ta.asset, ta.dt
    ),
    portfolio_asset_calendar AS (
        SELECT DISTINCT pda.tenant, pda.portfolio, pda.asset, bd.dt
        FROM position_deltas_agg pda
                 JOIN biz_dates bd ON bd.dt >= pda.dt
    ),
    daily_positions AS (
        SELECT
            pac.tenant, pac.portfolio, pac.asset, pac.dt,
            SUM(COALESCE(pda.delta_qty, 0)) OVER (
                PARTITION BY pac.tenant, pac.portfolio, pac.asset
                ORDER BY pac.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                ) AS position_qty
        FROM portfolio_asset_calendar pac
                 LEFT JOIN position_deltas_agg pda
                           ON pda.tenant = pac.tenant
                               AND pda.portfolio = pac.portfolio
                               AND pda.asset = pac.asset
                               AND pda.dt = pac.dt
    ),
    asset_mv AS (
        SELECT
            dp.tenant, dp.portfolio, dp.asset, dp.dt,
            (dp.position_qty * ah_last.close_price)::numeric(30,8) AS mv_end_asset
        FROM daily_positions dp
                 LEFT JOIN LATERAL (
            SELECT ah.close_price
            FROM asset_history ah
            WHERE ah.asset = dp.asset
              AND ah.price_date <= dp.dt
            ORDER BY ah.price_date DESC
            LIMIT 1
            ) ah_last ON TRUE
        WHERE ah_last.close_price IS NOT NULL
    ),

    /* <<< ALTERAÇÃO PARA USAR O VALOR PAGO NA TRANSAÇÃO >>> */
    transaction_flows AS (
        SELECT
            t.tenant,
            t.portfolio,
            t.asset,
            bd.dt,
            (
                CASE COALESCE(UPPER(t.type),'')
                    WHEN 'BUY'  THEN COALESCE(t.net_value,
                                              (ABS(COALESCE(t.quantity,0)) * COALESCE(t.price,0))
                                                  + COALESCE(t.fee_value,0) + COALESCE(t.tax_value,0))
                    WHEN 'SELL' THEN -COALESCE(t.net_value,
                                               (ABS(COALESCE(t.quantity,0)) * COALESCE(t.price,0))
                                                   - COALESCE(t.tax_value,0) - COALESCE(t.fee_value,0))
                    ELSE 0
                    END
                )::numeric(30,8) AS flow_value_asset
        FROM transaction t
                 LEFT JOIN LATERAL (
            SELECT bd.dt
            FROM biz_dates bd
            WHERE bd.dt >= t.trade_date::date
            ORDER BY bd.dt
            LIMIT 1
            ) bd ON TRUE
        WHERE t.trade_date <= current_date
    ),
    flow_by_asset AS (
        SELECT
            tf.tenant, tf.portfolio, tf.asset, tf.dt,
            SUM(tf.flow_value_asset)::numeric(30,8) AS flow_value_asset
        FROM transaction_flows tf
        WHERE tf.dt IS NOT NULL
        GROUP BY tf.tenant, tf.portfolio, tf.asset, tf.dt
    ),
    /* <<< FIM DA ALTERAÇÃO >>> */

    portfolio_mv AS (
        SELECT am.tenant, am.portfolio, am.dt, SUM(am.mv_end_asset) AS mv_end
        FROM asset_mv am
        GROUP BY am.tenant, am.portfolio, am.dt
    ),
    portfolio_flow AS (
        SELECT f.tenant, f.portfolio, f.dt, SUM(f.flow_value_asset) AS flow_value
        FROM flow_by_asset f
        GROUP BY f.tenant, f.portfolio, f.dt
    ),
    mv_with_prev AS (
        SELECT
            pmv.tenant, pmv.portfolio, pmv.dt, pmv.mv_end,
            coalesce(LAG(pmv.mv_end) OVER (PARTITION BY pmv.tenant, pmv.portfolio ORDER BY pmv.dt), 0) AS mv_prev,
            COALESCE(pf.flow_value, 0)::numeric(30,8) AS flow_value,
            SUM(COALESCE(pf.flow_value, 0)) OVER (
                PARTITION BY pmv.tenant, pmv.portfolio
                ORDER BY pmv.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                ) AS base_value
        FROM portfolio_mv pmv
                 LEFT JOIN portfolio_flow pf
                           ON pf.tenant = pmv.tenant
                               AND pf.portfolio = pmv.portfolio
                               AND pf.dt = pmv.dt
    ),
    returns AS (
        SELECT
            m.tenant, m.portfolio, m.dt,
            m.mv_end, m.mv_prev, m.flow_value,
            (m.mv_end - m.mv_prev - m.flow_value)::numeric(30,8) AS real_return_value,
            CASE
                WHEN m.mv_prev IS NULL OR m.mv_prev = 0 THEN NULL
                ELSE (m.mv_end - m.mv_prev - m.flow_value) / m.mv_prev
                END AS daily_return,
            m.base_value
        FROM mv_with_prev m
    )
SELECT
            ROW_NUMBER() OVER (ORDER BY r.tenant, r.portfolio, r.dt) AS seq_id,
            r.tenant,
            r.portfolio,
            p.name                                   AS portfolio_name,
            r.dt                                     AS price_date,
            r.mv_end::numeric(30,8)                  AS mv_end,
            r.mv_prev::numeric(30,8)                 AS mv_prev,
            r.flow_value::numeric(30,8)              AS flow_value,
            r.real_return_value::numeric(30,8)       AS real_return_value,
            r.daily_return::numeric(20,10)           AS daily_return,
            r.base_value::numeric(30,8)              AS base_value,
            (
                CASE
                    WHEN r.base_value IS NULL OR r.base_value = 0 THEN NULL
                    ELSE (
                                SUM(COALESCE(r.real_return_value, 0)) OVER (
                            PARTITION BY r.tenant, r.portfolio
                            ORDER BY r.dt
                            ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                            ) / r.base_value
                        )
                    END
                )::numeric(20,10)                        AS cumulative_return_percent,
            SUM(COALESCE(r.real_return_value, 0)) OVER (
                PARTITION BY r.tenant, r.portfolio
                ORDER BY r.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                )::numeric(30,8)                          AS cumulative_value_gain
FROM returns r
         LEFT JOIN portfolio p ON p.id = r.portfolio
ORDER BY r.tenant, r.portfolio, r.dt;


select *from vw_portfolio_daily_returns;

select * from asset_history
            inner join asset a on a.id = asset_history.asset
            where a.ticker = 'B3SA3'
and price_date between '2025-07-02' and '2025-12-30';