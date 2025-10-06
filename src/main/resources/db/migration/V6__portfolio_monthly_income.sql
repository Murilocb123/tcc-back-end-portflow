CREATE OR REPLACE VIEW vw_portfolio_monthly_income AS
WITH first_event AS (
    SELECT
        e.portfolio,
        MIN(DATE_TRUNC('month', e.event_date)) AS min_month
    FROM event e
    WHERE e.type IN ('DIVIDEND','JCP')
    GROUP BY e.portfolio
),
months AS (
    SELECT
        fe.portfolio,
        generate_series(
            fe.min_month,
            DATE_TRUNC('month', current_date),
            INTERVAL '1 month'
        )::date AS month_start
    FROM first_event fe
),
monthly_income AS (
    SELECT
        e.portfolio,
        DATE_TRUNC('month', e.event_date)::date AS month_start,
        SUM(e.total_value) AS income
    FROM event e
    WHERE e.type IN ('DIVIDEND','JCP')
    GROUP BY e.portfolio, DATE_TRUNC('month', e.event_date)
)
SELECT
    ROW_NUMBER() OVER (ORDER BY m.portfolio, m.month_start) AS seq_id,
    m.portfolio,
    p.name AS portfolio_name,
    m.month_start,
    COALESCE(mi.income, 0) AS income
FROM months m
LEFT JOIN monthly_income mi
    ON mi.portfolio = m.portfolio AND mi.month_start = m.month_start
LEFT JOIN portfolio p ON p.id = m.portfolio
ORDER BY m.portfolio, m.month_start;

