# Таблица пользователей
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255)
);

# Таблица пользовательских ролей
CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

# Таблица для привязка ролей к пользователям
CREATE TABLE user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

# Таблица продуктов
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(15, 2) NOT NULL
);

# Таблица заказов
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    address VARCHAR(255),
    phone VARCHAR(255),
    quantity DECIMAL(15, 3),
    sum DECIMAL(15, 2),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

# Таблица строк заказа
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    price DECIMAL(15, 2),
    quantity DECIMAL(15, 3),
    sum DECIMAL(15, 2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

INSERT INTO user (id, username, first_name, last_name, middle_name, password, email, address, phone)
VALUES (1, 'user', 'Ivan', 'Ivanov', '', '$2y$12$f5Vl52H5rrbxa5XTNB6Xx.rvxkuIrkf4w6rKvqdpq0aFYNhk50z0O', 'ivan@mail.ru', 'Ленина 47', '89881231231'),
       (2, 'admin', 'Petr', 'Petrov', '', '$2y$12$f5Vl52H5rrbxa5XTNB6Xx.rvxkuIrkf4w6rKvqdpq0aFYNhk50z0O', 'petr@mail.ru', '', '');

INSERT INTO role (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'SUPERADMIN'),
       (3, 'ROLE_USER');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 3),
       (2, 1);

INSERT INTO product (title, price)
VALUES
       ('Product 1', 1200.70),
       ('Product 2', 120.70),
       ('Product 3', 10.00),
       ('Product 4', 1202.70),
       ('Product 5', 1240.70),
       ('Product 6', 2200.88),
       ('Product 7', 3200.70),
       ('Product 8', 6200.70),
       ('Product 9', 14200.66),
       ('Product 10', 12500.70),
       ('Product 11', 1200.70),
       ('Product 12', 12060.55),
       ('Product 13', 120.60);