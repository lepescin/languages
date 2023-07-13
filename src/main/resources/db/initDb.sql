DROP TABLE IF EXISTS languages;

CREATE TABLE languages
(
    id               SERIAL PRIMARY KEY,
    name         VARCHAR NOT NULL,
    description VARCHAR,
    rating       INTEGER
);
