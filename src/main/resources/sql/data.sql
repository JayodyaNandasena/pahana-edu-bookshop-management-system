use pahana_edu_db;

-- ==== CATEGORY ====
INSERT INTO category (name) VALUES
('Books'),
('Stationery'),
('Educational Material'),
('Gift Items'),
('Miscellaneous');

-- ==== ITEM ====
INSERT INTO item (name, unit_price, quantity_available, category_id) VALUES
-- Books (category_id = 1)
('The Great Gatsby', 1500.00, 20, 1),
('Harry Potter and the Philosopher\'s Stone', 2500.00, 15, 1),
('Sherlock Holmes Collection', 3200.00, 10, 1),
('A5 Spiral Notebook', 250.00, 60, 2),
('Ballpoint Pen (Blue)', 50.00, 200, 2),
('Pencil Pack (HB)', 120.00, 100, 2),
('Oxford English Dictionary', 3500.00, 10, 3),
('Science Experiment Kit', 4500.00, 5, 3),
('Mathematics Practice Workbook', 800.00, 25, 3),
('Greeting Card', 200.00, 40, 4),
('Bookmark Set', 300.00, 30, 4),
('Book Lamp', 1500.00, 15, 4),
('Reusable Tote Bag', 500.00, 20, 5),
('Book Cover Plastic Roll', 350.00, 50, 5),
('Sticky Notes', 180.00, 80, 5);

-- ==== CUSTOMER ====
INSERT INTO customer (first_name, last_name, email, phone, address, units_consumed, is_active) VALUES
('John', 'Doe', 'john@example.com', '1234567890', '123 Elm St', 15, TRUE),
('Jane', 'Smith', 'jane@example.com', '0987654321', '456 Oak St', 20, TRUE),
('Bob', 'Brown', NULL, '1112223333', '789 Pine St', 5, TRUE),
('Alice', 'Johnson', 'alice@example.com', NULL, '321 Birch St', 0, FALSE),
('Eve', 'Davis', 'eve@example.com', '5554443333', '654 Cedar St', 10, TRUE);


-- ==== USER ====
INSERT INTO user (name, username, password_hash, user_type) VALUES
('Admin One', 'admin1', 'hash1', 'ADMIN'),
('Cashier A', 'cashiera', 'hash2', 'STAFF'),
('Staff Member', 'staffm', 'hash3', 'STAFF'),
('User X', 'userx', 'hash4', 'STAFF'),
('Manager Y', 'managery', 'hash5', 'ADMIN');

-- ==== BILL ====
INSERT INTO bill (customer_id, cashier_id, bill_date, bill_time, total) VALUES
(1, 1, '2025-07-01', '10:00:00', 1800.00),
(2, 2, '2025-07-02', '11:15:00', 550.00),
(3, 3, '2025-07-03', '12:30:00', 3200.00),
(4, 4, '2025-07-04', '14:00:00', 1700.00),
(5, 5, '2025-07-05', '15:45:00', 680.00);

-- ==== BILL ITEM ====
INSERT INTO bill_item (bill_id, item_id, quantity, unit_price) VALUES
(1, 1, 1, 1500.00),
(1, 5, 2, 50.00),
(2, 4, 2, 250.00),
(2, 5, 1, 50.00),
(3, 3, 1, 3200.00),
(4, 12, 1, 1500.00),
(4, 11, 1, 200.00),
(5, 13, 1, 500.00),
(5, 15, 1, 180.00);
