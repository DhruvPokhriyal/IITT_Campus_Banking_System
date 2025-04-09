-- Insert default admin accounts
-- Plain text password: 'admin123'
INSERT INTO admins
    (name, email, password)
VALUES
    ('Super Admin', 'superadmin@iitbank.com', 'admin123'),
    ('System Admin', 'systemadmin@iitbank.com', 'admin123');

-- Insert a test user account
-- Plain text password: 'user123'
INSERT INTO users
    (name, email, password)
VALUES
    ('Test User', 'testuser@iitbank.com', 'user123');

-- Insert a test account for the test user
INSERT INTO accounts
    (account_number, balance, user_id)
VALUES
    (123456, 1000.00, 1);

-- Insert admin user
INSERT INTO admins
    (name, email, password)
VALUES
    ('Admin User', 'admin@iitbank.com', 'admin123');

-- Insert test users
INSERT INTO users
    (name, email, password)
VALUES
    ('John Doe', 'john@example.com', 'password123'),
    ('Jane Smith', 'jane@example.com', 'password123');

-- Insert test accounts
INSERT INTO accounts
    (account_number, balance, user_id)
VALUES
    (1001, 1000.00, 1),
    (1002, 2000.00, 2);

-- Insert test transactions
INSERT INTO transactions
    (transaction_type, amount, description, from_account_id, to_account_id)
VALUES
    ('deposit', 500.00, 'Initial deposit', NULL, 1),
    ('transfer', 200.00, 'Transfer to Jane', 1, 2);
