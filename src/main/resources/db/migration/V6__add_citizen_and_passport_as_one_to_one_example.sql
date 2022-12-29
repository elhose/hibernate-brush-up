CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');

CREATE TABLE IF NOT EXISTS passport
(
    id            BIGSERIAL PRIMARY KEY,
    passport_uuid UUID,
    creation_date TIMESTAMP WITH TIME ZONE,
    valid_until   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS citizen
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR,
    last_name   VARCHAR,
    gender      gender_type,
    passport_id BIGSERIAL,
    CONSTRAINT passport_id_fk FOREIGN KEY (passport_id) REFERENCES passport (id)
);
