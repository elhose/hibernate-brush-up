CREATE TABLE IF NOT EXISTS cart
(
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS product
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR,
    price   DECIMAL,
    cart_id BIGSERIAL,
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart (id)
);
