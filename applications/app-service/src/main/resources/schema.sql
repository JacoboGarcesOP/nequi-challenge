CREATE SCHEMA IF NOT EXISTS nequi_schema;

CREATE TABLE IF NOT EXISTS nequi_schema.franchise (
    franchise_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_franchise_name ON nequi_schema.franchise(name);


-- Branches table: one-to-many with franchise
CREATE TABLE IF NOT EXISTS nequi_schema.branch (
    branch_id BIGSERIAL PRIMARY KEY,
    franchise_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT fk_branch_franchise
        FOREIGN KEY (franchise_id)
        REFERENCES nequi_schema.franchise(franchise_id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_branch_franchise_id ON nequi_schema.branch(franchise_id);

-- Products table: one-to-many with branch
CREATE TABLE IF NOT EXISTS nequi_schema.product (
    product_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT fk_product_branch
        FOREIGN KEY (branch_id)
        REFERENCES nequi_schema.branch(branch_id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_product_branch_id ON nequi_schema.product(branch_id);

