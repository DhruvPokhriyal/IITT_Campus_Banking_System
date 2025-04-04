-- Insert default admin accounts
-- Password for both accounts is 'admin123' (hashed with BCrypt)
INSERT INTO admin
    (name, email, password)
VALUES
    ('Super Admin', 'superadmin@iitbank.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'),
    ('System Admin', 'systemadmin@iitbank.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Insert a test user account
-- Password is 'user123' (hashed with BCrypt)
INSERT INTO "user"
    (name, email, password)
VALUES
    ('Test User', 'testuser@iitbank.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Insert a test account for the test user
INSERT INTO account
    (account_number, balance, user_id)
VALUES
    ('ACC123456', 1000.00, 1); 