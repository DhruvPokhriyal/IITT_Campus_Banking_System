-- ======================
-- DROP EXISTING TABLES
-- ======================
DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS account
CASCADE;
DROP TABLE IF EXISTS admin
CASCADE;
DROP TABLE IF EXISTS "user"
CASCADE;

-- ======================
-- CREATE TABLE: admin
-- ======================
CREATE TABLE admin
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ======================
-- CREATE TABLE: user
-- ======================
CREATE TABLE "user"
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ======================
-- CREATE TABLE: account
-- ======================
CREATE TABLE account
(
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(19, 4) NOT NULL DEFAULT 0.0000,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

-- ===========================
-- CREATE TABLE: transaction
-- ===========================
CREATE TABLE transaction
(
    id BIGSERIAL PRIMARY KEY,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('deposit', 'withdrawal', 'transfer')),
    amount DECIMAL(19, 4) NOT NULL CHECK (amount > 0),
    description TEXT,
    from_account_id BIGINT,
    to_account_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_account_id) REFERENCES account(id) ON DELETE SET NULL,
    FOREIGN KEY (to_account_id) REFERENCES account(id) ON DELETE SET NULL
);

-- ======================
-- CREATE INDEXES
-- ======================
CREATE INDEX idx_account_user_id ON account(user_id);
CREATE INDEX idx_transaction_from_account_id ON transaction(from_account_id);
CREATE INDEX idx_transaction_to_account_id ON transaction(to_account_id);
