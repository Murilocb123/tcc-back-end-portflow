DROP
EXTENSION IF EXISTS "uuid-ossp";
CREATE
EXTENSION "uuid-ossp";

CREATE TABLE app_user
(
    id         UUID         NOT NULL,
    tenant     UUID         NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    active     BOOLEAN      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE asset
(
    id         UUID         NOT NULL,
    ticker     VARCHAR(255),
    name       VARCHAR(255) NOT NULL,
    type       VARCHAR(255) NOT NULL,
    currency   VARCHAR(3)   NOT NULL,
    exchange   VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT pk_asset PRIMARY KEY (id)
);

CREATE TABLE asset_history
(
    asset       UUID           NOT NULL,
    price_date  date           NOT NULL,
    close_price DECIMAL(18, 6) NOT NULL,
    open_price  DECIMAL(18, 6),
    high_price  DECIMAL(18, 6),
    low_price   DECIMAL(18, 6),
    volume      BIGINT,
    dividends   DECIMAL(18, 6),
    splits      DECIMAL(18, 6),
    CONSTRAINT pk_asset_history PRIMARY KEY (asset, price_date)
);


CREATE TABLE asset_log
(
    id         UUID         NOT NULL,
    ticker     VARCHAR(255),
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    service    VARCHAR(255) NOT NULL,
    level      VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT pk_asset_log PRIMARY KEY (id)
);

CREATE TABLE broker
(
    id         UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    cnpj       VARCHAR(14)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT pk_broker PRIMARY KEY (id)
);

CREATE TABLE event
(
    id          UUID         NOT NULL,
    portfolio   UUID         NOT NULL,
    tenant      UUID         NOT NULL,
    broker      UUID,
    asset       UUID         NOT NULL,
    type        VARCHAR(255) NOT NULL,
    event_date  date,
    total_value DECIMAL(18, 2),
    notes       VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE portfolio
(
    id                UUID         NOT NULL,
    name              VARCHAR(255) NOT NULL,
    default_portfolio BOOLEAN      NOT NULL,
    tenant            UUID         NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        UUID,
    updated_by        UUID,
    CONSTRAINT pk_portfolio PRIMARY KEY (id)
);


CREATE TABLE portfolio_asset
(
    id               UUID            NOT NULL,
    portfolio        UUID            NOT NULL,
    tenant           UUID            NOT NULL,
    quantity         DECIMAL(28, 10) NOT NULL,
    average_price    DECIMAL(18, 2)  NOT NULL,
    total_fee        DECIMAL(18, 2)  NOT NULL DEFAULT 0,
    total_tax        DECIMAL(18, 2)  NOT NULL DEFAULT 0,
    broker           UUID,
    asset            UUID            NOT NULL,
    total_receivable DECIMAL(18, 2)  NOT NULL DEFAULT 0,
    start_date       date            NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    updated_by       UUID,
    CONSTRAINT pk_portfolio_asset PRIMARY KEY (id)
);

CREATE TABLE tenant
(
    id         UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tenant PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id          UUID         NOT NULL,
    portfolio   UUID         NOT NULL,
    tenant      UUID         NOT NULL,
    broker      UUID,
    asset       UUID         NOT NULL,
    type        VARCHAR(255) NOT NULL,
    trade_date  date         NOT NULL,
    quantity    DECIMAL(28, 10),
    price       DECIMAL(18, 6),
    gross_value DECIMAL(18, 2),
    net_value   DECIMAL(18, 2),
    fee_value   DECIMAL(18, 2),
    tax_value   DECIMAL(18, 2),
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

CREATE TABLE asset_forecast
(
    id            UUID           NOT NULL PRIMARY KEY,
    asset         UUID           NOT NULL,
    forecast_date DATE           NOT NULL,
    yhat          DECIMAL(18, 6) NOT NULL,
    yhat_lower    DECIMAL(18, 6),
    yhat_upper    DECIMAL(18, 6),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by    UUID,
    CONSTRAINT fk_asset_forecast_asset FOREIGN KEY (asset) REFERENCES asset (id)
);


-- FKs and Unique Constraints
ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE broker
    ADD CONSTRAINT uc_broker_cnpj UNIQUE (cnpj);

ALTER TABLE asset
    ADD CONSTRAINT uk_asset_ticker_exchange UNIQUE (ticker, exchange);

ALTER TABLE portfolio_asset
    ADD CONSTRAINT uk_portfolio_asset_asset_portfolio_broker UNIQUE (asset, portfolio, broker);

ALTER TABLE portfolio
    ADD CONSTRAINT uk_portfolio_name_tenant UNIQUE (name, tenant);

ALTER TABLE asset_history
    ADD CONSTRAINT FK_ASSET_HISTORY_ON_ASSET FOREIGN KEY (asset) REFERENCES asset (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_ASSET FOREIGN KEY (asset) REFERENCES asset (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_BROKER FOREIGN KEY (broker) REFERENCES broker (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_PORTFOLIO FOREIGN KEY (portfolio) REFERENCES portfolio (id);

ALTER TABLE portfolio_asset
    ADD CONSTRAINT FK_PORTFOLIO_ASSET_ON_ASSET FOREIGN KEY (asset) REFERENCES asset (id);

ALTER TABLE portfolio_asset
    ADD CONSTRAINT FK_PORTFOLIO_ASSET_ON_BROKER FOREIGN KEY (broker) REFERENCES broker (id);

ALTER TABLE portfolio_asset
    ADD CONSTRAINT FK_PORTFOLIO_ASSET_ON_PORTFOLIO FOREIGN KEY (portfolio) REFERENCES portfolio (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_ASSET FOREIGN KEY (asset) REFERENCES asset (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_BROKER FOREIGN KEY (broker) REFERENCES broker (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_PORTFOLIO FOREIGN KEY (portfolio) REFERENCES portfolio (id);


-- Indexes
-- app_user
CREATE INDEX ix_app_user_tenant ON app_user (tenant);
CREATE INDEX ix_app_user_active ON app_user (active);

-- asset_history (muito acessada)
CREATE INDEX ix_asset_history_price_date ON asset_history (price_date);
CREATE INDEX ix_asset_history_date_asset ON asset_history (price_date, asset);

-- event
CREATE INDEX ix_event_portfolio ON event (portfolio);
CREATE INDEX ix_event_asset ON event (asset);
CREATE INDEX ix_event_broker ON event (broker);
CREATE INDEX ix_event_tenant ON event (tenant);
CREATE INDEX ix_event_date ON event (event_date);

-- portfolio_asset
CREATE INDEX ix_portfolio_asset_portfolio ON portfolio_asset (portfolio);
CREATE INDEX ix_portfolio_asset_asset ON portfolio_asset (asset);
CREATE INDEX ix_portfolio_asset_broker ON portfolio_asset (broker);
CREATE INDEX ix_portfolio_asset_tenant ON portfolio_asset (tenant);
CREATE INDEX ix_portfolio_asset_portfolio_asset ON portfolio_asset (portfolio, asset);

-- transaction
CREATE INDEX ix_transaction_portfolio ON transaction (portfolio);
CREATE INDEX ix_transaction_asset ON transaction (asset);
CREATE INDEX ix_transaction_broker ON transaction (broker);
CREATE INDEX ix_transaction_tenant ON transaction (tenant);
CREATE INDEX ix_transaction_trade_date ON transaction (trade_date);
CREATE INDEX ix_transaction_portfolio_date ON transaction (portfolio, trade_date);
CREATE INDEX ix_transaction_asset_date ON transaction (asset, trade_date);
