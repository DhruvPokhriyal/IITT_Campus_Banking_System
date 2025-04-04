-- Insert default admin accounts
-- Plain text password: 'admin123'
INSERT INTO admin
    (name, email, password)
VALUES
    ('Super Admin', 'superadmin@iitbank.com', 'admin123'),
    ('System Admin', 'systemadmin@iitbank.com', 'admin123');

-- Insert a test user account
-- Plain text password: 'user123'
INSERT INTO "user"
    (name, email, password)
VALUES
    ('Test User', 'testuser@iitbank.com', 'user123');

-- Insert a test account for the test user
INSERT INTO account
    (account_number, balance, user_id)
VALUES
    ('ACC123456', 1000.00, 1);
