DROP TABLE IF EXISTS CUSTOMER CASCADE;
DROP TABLE IF EXISTS PRODUCT CASCADE;
DROP TABLE IF EXISTS CUSTOMER_ORDER CASCADE;
DROP TABLE IF EXISTS SHOPPING_CART CASCADE;
DROP TABLE IF EXISTS CART_PRODUCTS CASCADE;


create table customer
(
    id    bigint auto_increment primary key,
    name  varchar(255),
    email varchar(255)
);

create table product
(
    id    bigint auto_increment
        primary key,
    name  varchar(255) null,
    price double null
);
CREATE TABLE customer_order
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    status      VARCHAR(255),
    customer_id BIGINT,
    product_id  BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

create table shopping_cart
(
    id          bigint auto_increment
        primary key,
    customer_id bigint
);
create table cart_products
(
    cart_id    bigint,
    product_id bigint
);

INSERT INTO customer (id, name, email)
VALUES (1, 'Ravi Kumar', 'ravi.kumar@example.com'),
       (2, 'Anjali Sharma', 'anjali.sharma@example.com'),
       (3, 'Vikram Singh', 'vikram.singh@example.com'),
       (4, 'Pooja Patel', 'pooja.patel@example.com'),
       (5, 'Amitabh Bachchan', 'amitabh.bachchan@example.com'),
       (6, 'Sita Desai', 'sita.desai@example.com'),
       (7, 'Arjun Reddy', 'arjun.reddy@example.com'),
       (8, 'Neha Gupta', 'neha.gupta@example.com'),
       (9, 'Rajesh Khanna', 'rajesh.khanna@example.com'),
       (10, 'Priya Mehta', 'priya.mehta@example.com');

INSERT INTO product (id, name, price)
VALUES (1, 'Bajaj Mixer', 1200.00),
       (2, 'Godrej Refrigerator', 15000.00),
       (3, 'Tata Salt', 25.00),
       (4, 'Amul Butter', 45.00),
       (5, 'Samsung Mobile', 15000.00),
       (6, 'Hero Bicycle', 5500.00),
       (7, 'Parle G Biscuits', 10.00),
       (8, 'Haldiram Sweets', 500.00),
       (9, 'Titan Watch', 3000.00),
       (10, 'Raymond Suit', 8500.00);

INSERT INTO customer_order (id, status, customer_id, product_id)
VALUES (1, 'NEW', 1, 1),
       (2, 'SHIPPED', 2, 2),
       (3, 'DELIVERED', 3, 3),
       (4, 'CANCELLED', 4, 4),
       (5, 'NEW', 5, 5),
       (6, 'NEW', 6, 6),
       (7, 'SHIPPED', 7, 7),
       (8, 'DELIVERED', 8, 8),
       (9, 'CANCELLED', 9, 9),
       (10, 'NEW', 10, 10);

INSERT INTO shopping_cart (id, customer_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

INSERT INTO cart_products (cart_id, product_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10),
       (6, 1),
       (6, 3),
       (7, 5),
       (7, 7),
       (8, 9),
       (8, 2),
       (9, 4),
       (9, 6),
       (10, 8),
       (10, 10);
