CREATE TABLE IF NOT EXISTS author
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR,
    last_name   VARCHAR
);

CREATE TABLE IF NOT EXISTS book
(
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR,
    price           DECIMAL,
    is_on_promotion BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS author_book_relationship
(
    author_id BIGSERIAL,
    book_id   BIGSERIAL,
    PRIMARY KEY (author_id, book_id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book (id)
);
