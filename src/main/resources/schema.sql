CREATE TABLE `ACCOUNT`(
    id INT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(50) DEFAULT 'Chưa có tên',
    user_name VARCHAR(50) NOT NULL,
    pwd VARCHAR(255) NOT NULL,
    image LONGBLOB,
    role_id INT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(user_name)
); CREATE TABLE `CARTITEM`(
    id INT NOT NULL AUTO_INCREMENT,
    account_id INT NOT NULL,
    quantity INT DEFAULT 1,
    book_id INT NOT NULL,
    order_date DATE NOT NULL,
    status BOOLEAN
            DEFAULT 0,
    PRIMARY KEY(id)
); CREATE TABLE `AUTHOR`(
    id INT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(50) NOT NULL,
    date_of_brith DATE,
    country VARCHAR(30) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(full_name)
); CREATE TABLE `BOOK`(
    id INT NOT NULL AUTO_INCREMENT,
    author_id INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    image LONGBLOB,
    price FLOAT NOT NULL,
    publish_year INT(4),
    category_id INT NOT NULL,
    description VARCHAR(1000) DEFAULT 'Hiện chưa có mô tả',
    PRIMARY KEY(id),
    UNIQUE(title)
); CREATE TABLE `ROLE`(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(NAME)
); CREATE TABLE `CATEGORY`(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(NAME)
); CREATE TABLE `SHIPPING`(
    id INT NOT NULL AUTO_INCREMENT,
    account_id INT NOT NULL,
    card_owner VARCHAR(30),
    card_number INT NOT NULL,
    address VARCHAR(30) NOT NULL,
    city VARCHAR(30) NOT NULL,
    number_phone INT NOT NULL,
    status BOOLEAN
        DEFAULT 0,
        PRIMARY KEY(id)
);
ALTER TABLE
    `ACCOUNT` ADD CONSTRAINT fk_account_role FOREIGN KEY(role_id) REFERENCES ROLE(id);
ALTER TABLE
    `CARTITEM` ADD CONSTRAINT fk_cartitem_account FOREIGN KEY(account_id) REFERENCES ACCOUNT(id);
ALTER TABLE
    `CARTITEM` ADD CONSTRAINT fk_cartitem_book FOREIGN KEY(book_id) REFERENCES BOOK(id);
ALTER TABLE
    `BOOK` ADD CONSTRAINT fk_book_author FOREIGN KEY(author_id) REFERENCES AUTHOR(id);
ALTER TABLE
    `BOOK` ADD CONSTRAINT fk_book_category FOREIGN KEY(category_id) REFERENCES CATEGORY(id);
ALTER TABLE
    `SHIPPING` ADD CONSTRAINT fk_shipping_account FOREIGN KEY(account_id) REFERENCES ACCOUNT(id);