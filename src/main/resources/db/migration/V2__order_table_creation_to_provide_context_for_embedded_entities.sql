CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS order_table
(
    id                                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    consumer                             VARCHAR,
    billing_address_street_with_details  VARCHAR,
    billing_address_city                 VARCHAR,
    billing_address_state                VARCHAR,
    billing_address_zip_code             VARCHAR,
    shipping_address_street_with_details VARCHAR,
    shipping_address_city                VARCHAR,
    shipping_address_state               VARCHAR,
    shipping_address_zip_code            VARCHAR
);