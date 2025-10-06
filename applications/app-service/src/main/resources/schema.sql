CREATE SCHEMA IF NOT EXISTS nequi_schema;

CREATE TABLE IF NOT EXISTS nequi_schema.franchise (
    franchise_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_franchise_name ON nequi_schema.franchise(name);


