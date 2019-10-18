DROP TABLE IF EXISTS app.migrations;
CREATE TABLE app.migrations
(
    ID           VARCHAR(36) PRIMARY KEY,
    VERSION      VARCHAR(4),
    DESCRIPTION  VARCHAR(45),
    UP           VARCHAR(10000),
    DOWN         VARCHAR(10000),
    DID_RUN_LAST BOOLEAN DEFAULT TRUE
);