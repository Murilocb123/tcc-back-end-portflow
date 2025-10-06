DROP VIEW IF EXISTS vw_portfolio_asset_daily_returns CASCADE;

CREATE OR REPLACE VIEW vw_portfolio_asset_daily_returns AS
WITH
    biz_dates AS (
        SELECT DISTINCT ah.price_date::date AS dt
        FROM asset_history ah
        WHERE ah.price_date <= current_date
    ),
    transactions_aligned AS (
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
            ta.tenant,
            ta.portfolio,
            ta.asset,
            ta.dt,
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
            pac.tenant,
            pac.portfolio,
            pac.asset,
            pac.dt,
            SUM(COALESCE(pda.delta_qty, 0)) OVER (
                PARTITION BY pac.tenant, pac.portfolio, pac.asset
                ORDER BY pac.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                ) AS position_qty
        FROM portfolio_asset_calendar pac
                 LEFT JOIN position_deltas_agg pda
                           ON pda.tenant    = pac.tenant
                               AND pda.portfolio = pac.portfolio
                               AND pda.asset     = pac.asset
                               AND pda.dt        = pac.dt
    ),
    asset_mv AS (
        SELECT
            dp.tenant,
            dp.portfolio,
            dp.asset,
            dp.dt,
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

    /* <<< ALTERAÇÃO: usar valor pago/recebido na transação >>> */
    transaction_flows AS (
        SELECT
            t.tenant,
            t.portfolio,
            t.asset,
            bd.dt,
            (
                CASE COALESCE(UPPER(t.type),'')
                    WHEN 'BUY'  THEN COALESCE(
                            t.net_value,
                            (ABS(COALESCE(t.quantity,0)) * COALESCE(t.price,0))
                                + COALESCE(t.fee_value,0) + COALESCE(t.tax_value,0)
                                     )
                    WHEN 'SELL' THEN -COALESCE(
                            t.net_value,
                            (ABS(COALESCE(t.quantity,0)) * COALESCE(t.price,0))
                                - COALESCE(t.tax_value,0) - COALESCE(t.fee_value,0)
                                      )
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

    mv_with_prev AS (
        SELECT
            am.tenant,
            am.portfolio,
            am.asset,
            am.dt,
            am.mv_end_asset,
            COALESCE(LAG(am.mv_end_asset) OVER (
                PARTITION BY am.tenant, am.portfolio, am.asset
                ORDER BY am.dt
                ), 0) AS mv_prev_asset,
            COALESCE(f.flow_value_asset, 0)::numeric(30,8) AS flow_value_asset,
            SUM(COALESCE(f.flow_value_asset, 0)) OVER (
                PARTITION BY am.tenant, am.portfolio, am.asset
                ORDER BY am.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                ) AS base_value_asset
        FROM asset_mv am
                 LEFT JOIN flow_by_asset f
                           ON f.tenant    = am.tenant
                               AND f.portfolio = am.portfolio
                               AND f.asset     = am.asset
                               AND f.dt        = am.dt
    ),
    returns_asset AS (
        SELECT
            m.tenant,
            m.portfolio,
            m.asset,
            m.dt,
            m.mv_end_asset,
            m.mv_prev_asset,
            m.flow_value_asset,
            (m.mv_end_asset - m.mv_prev_asset - m.flow_value_asset)::numeric(30,8) AS real_return_value_asset,
            CASE
                WHEN m.mv_prev_asset IS NULL OR m.mv_prev_asset = 0 THEN NULL
                ELSE (m.mv_end_asset - m.mv_prev_asset - m.flow_value_asset) / m.mv_prev_asset
                END AS daily_return_asset,
            m.base_value_asset
        FROM mv_with_prev m
    )
SELECT
            ROW_NUMBER() OVER (ORDER BY r.tenant, r.portfolio, r.asset, r.dt) AS seq_id,
            r.tenant,
            r.portfolio,
            p.name                                              AS portfolio_name,
            r.asset,
            r.dt                                                AS price_date,
            r.mv_end_asset::numeric(30,8)                       AS mv_end_asset,
            r.mv_prev_asset::numeric(30,8)                      AS mv_prev_asset,
            r.flow_value_asset::numeric(30,8)                   AS flow_value_asset,
            r.real_return_value_asset::numeric(30,8)            AS real_return_value_asset,  -- R$ do dia por ativo
            r.daily_return_asset::numeric(20,10)                AS daily_return_asset,        -- fração por ativo
            r.base_value_asset::numeric(30,8)                   AS base_value_asset,
            (
                CASE
                    WHEN r.base_value_asset IS NULL OR r.base_value_asset = 0 THEN NULL
                    ELSE
                        (SUM(COALESCE(r.real_return_value_asset, 0)) OVER (
                            PARTITION BY r.tenant, r.portfolio, r.asset
                            ORDER BY r.dt
                            ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                            ) / r.base_value_asset)
                    END
                )::numeric(20,10)                                   AS cumulative_return_percent_asset, -- ROI sobre aportes do ativo
            SUM(COALESCE(r.real_return_value_asset, 0)) OVER (
                PARTITION BY r.tenant, r.portfolio, r.asset
                ORDER BY r.dt
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                )::numeric(30,8)                                    AS cumulative_value_gain_asset
FROM returns_asset r
         LEFT JOIN portfolio p ON p.id = r.portfolio
ORDER BY r.tenant, r.portfolio, r.asset, r.dt;
