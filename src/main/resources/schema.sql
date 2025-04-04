-- Drop existing tables if they exist
DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS account
CASCADE;
DROP TABLE IF EXISTS admin
CASCADE;
DROP TABLE IF EXISTS "user"
CASCADE;

-- Create admin table
CREATE TABLE admin
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create user table
CREATE TABLE "user"
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create account table
CREATE TABLE account
(
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Create transaction table
CREATE TABLE transaction
(
    id BIGSERIAL PRIMARY KEY,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    description TEXT,
    from_account_id BIGINT,
    to_account_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_account_id) REFERENCES account(id),
    FOREIGN KEY (to_account_id) REFERENCES account(id)
);

-- Create indexes
CREATE INDEX idx_account_user_id ON account(user_id);
CREATE INDEX idx_transaction_from_account_id ON transaction(from_account_id);
CREATE INDEX idx_transaction_to_account_id ON transaction(to_account_id); 