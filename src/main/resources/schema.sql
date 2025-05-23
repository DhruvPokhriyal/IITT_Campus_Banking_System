-- ======================
-- DROP EXISTING TABLES
-- ======================
DROP TABLE IF EXISTS transactions
CASCADE;
DROP TABLE IF EXISTS accounts
CASCADE;
DROP TABLE IF EXISTS users
CASCADE;
DROP TABLE IF EXISTS admin
CASCADE;

-- ======================
-- CREATE TABLE: admin
-- ======================
CREATE TABLE admins
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ======================
-- CREATE TABLE: user
-- ======================
CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ======================
-- CREATE TABLE: account
-- ======================
CREATE TABLE accounts
(
    id BIGSERIAL PRIMARY KEY,
    account_number BIGINT UNIQUE NOT NULL,
    balance DECIMAL(19, 4) NOT NULL DEFAULT 0.0000,
    user_id BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ===========================
-- CREATE TABLE: transaction
-- ===========================
CREATE TABLE transactions
(
    id BIGSERIAL PRIMARY KEY,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('Deposit', 'Withdrawal', 'Transfer')),
    amount DECIMAL(19, 4) NOT NULL CHECK (amount > 0),
    description VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    from_account_id BIGINT,
    to_account_id BIGINT,
    FOREIGN KEY (from_account_id) REFERENCES accounts(id) ON DELETE SET NULL,
    FOREIGN KEY (to_account_id) REFERENCES accounts(id) ON DELETE SET NULL
);

-- ======================
-- CREATE INDEXES
-- ======================
CREATE INDEX idx_account_user_id ON accounts(user_id);
CREATE INDEX idx_transaction_from_account ON transactions(from_account_id);
CREATE INDEX idx_transaction_to_account ON transactions(to_account_id);
CREATE INDEX idx_transaction_created_at ON transactions(created_at);

-- ======================
-- CLEANUP SCRIPT
-- ======================
-- Remove duplicate accounts (keeping the one with the lowest ID)
DELETE FROM accounts a1
WHERE EXISTS (
    SELECT 1
FROM accounts a2
WHERE a2.account_number = a1.account_number
    AND a2.id < a1.id
);

-- Remove accounts without associated users
DELETE FROM accounts
WHERE user_id NOT IN (SELECT id
FROM users);

-- Remove transactions without associated accounts
DELETE FROM transactions
WHERE (from_account_id IS NOT NULL AND from_account_id NOT IN (SELECT id
    FROM accounts))
    OR (to_account_id IS NOT NULL AND to_account_id NOT IN (SELECT id
    FROM accounts));
