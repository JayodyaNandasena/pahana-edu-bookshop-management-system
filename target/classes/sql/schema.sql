-- Create database
CREATE DATABASE IF NOT EXISTS pahana_edu_db;

USE pahana_edu_db;

-- ==== CATEGORY ====
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- ==== ITEM ====
CREATE TABLE item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    quantity_available INT NOT NULL DEFAULT 0,
    category_id BIGINT NOT NULL,
    is_deleted BOOL NOT NULL DEFAULT 0,
    UNIQUE KEY uq_item_name_category (name, category_id),
    CONSTRAINT fk_item_category FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ==== CUSTOMER ====
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(30) UNIQUE,
    address TEXT,
    units_consumed INT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- ==== USER ====
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    user_type ENUM('ADMIN', 'STAFF') NOT NULL
);

-- ==== BILL ==== 
CREATE TABLE bill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    cashier_id BIGINT NOT NULL,
    bill_date DATE NOT NULL,
    bill_time TIME NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    CONSTRAINT chk_total_nonnegative CHECK (total >= 0),
    CONSTRAINT fk_bill_customer FOREIGN KEY (customer_id)
        REFERENCES customer(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_bill_user FOREIGN KEY (cashier_id)
        REFERENCES user(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ==== BILL ITEM ====
CREATE TABLE bill_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bill_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    UNIQUE KEY uq_billitem (bill_id, item_id),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0),
    CONSTRAINT chk_unit_price_nonnegative CHECK (unit_price >= 0),
    CONSTRAINT fk_billitem_bill FOREIGN KEY (bill_id)
        REFERENCES bill(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_billitem_item FOREIGN KEY (item_id)
        REFERENCES item(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ==== Indexes ==== 
CREATE INDEX idx_item_category     ON item(category_id);
CREATE INDEX idx_bill_customer     ON bill(customer_id);
CREATE INDEX idx_bill_user         ON bill(cashier_id);
CREATE INDEX idx_billitem_bill     ON bill_item(bill_id);
CREATE INDEX idx_billitem_item     ON bill_item(item_id);
