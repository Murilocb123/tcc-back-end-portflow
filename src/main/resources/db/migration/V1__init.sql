CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";


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
    CONSTRAINT pk_asset_history PRIMARY KEY (asset, price_date)
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
    id              UUID         NOT NULL,
    portfolio       UUID         NOT NULL,
    tenant          UUID         NOT NULL,
    broker          UUID,
    asset           UUID         NOT NULL,
    type            VARCHAR(255) NOT NULL,
    ex_date         date         NOT NULL,
    pay_date        date,
    value_per_share DECIMAL(18, 6),
    total_value     DECIMAL(18, 2),
    currency        VARCHAR(3)   NOT NULL,
    notes           VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      UUID,
    updated_by      UUID,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE portfolio
(
    id               UUID         NOT NULL,
    name             VARCHAR(255) NOT NULL,
    default_porfolio BOOLEAN      NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    updated_by       UUID,
    CONSTRAINT pk_portfolio PRIMARY KEY (id)
);

CREATE TABLE portfolio_asset
(
    id            UUID             NOT NULL,
    portfolio     UUID             NOT NULL,
    tenant        UUID             NOT NULL,
    quantity      DOUBLE PRECISION NOT NULL,
    average_price DOUBLE PRECISION NOT NULL,
    total_value   DOUBLE PRECISION NOT NULL,
    broker        UUID,
    asset         UUID             NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
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

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE broker
    ADD CONSTRAINT uc_broker_cnpj UNIQUE (cnpj);

ALTER TABLE asset
    ADD CONSTRAINT uk_asset_ticker_exchange UNIQUE (ticker, exchange);

ALTER TABLE portfolio_asset
    ADD CONSTRAINT uk_portfolio_asset_asset_portfolio_broker UNIQUE (asset, broker);

ALTER TABLE portfolio
    ADD CONSTRAINT uk_portfolio_name_tenant UNIQUE (name);

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