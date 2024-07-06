CREATE DATABASE library_db;
USE library_db;
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255),
    year_published INT
);
CREATE TABLE members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20)
);
CREATE TABLE loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    member_id INT,
    loan_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
INSERT INTO books (book_id,title,author,year_published) VALUES ("1","Harry Potter","J K Rowling","1997");
INSERT INTO books (book_id,title,author,year_published) VALUES ("2","Gamperaliya","Martin Wickramasinghe","1944");
INSERT INTO books (book_id,title,author,year_published) VALUES ("3","The Loard of the Rings","R R Tolkien","1937");

INSERT INTO members (member_id,name,email,phone) VALUES ("001","Mevini Silva","mevininishshanka21@gmail.com","0718797868");
INSERT INTO members (member_id,name,email,phone) VALUES ("002","Hiruni Silva","hiruninishshanka98@gmail.com","0712345432");
INSERT INTO members (member_id,name,email,phone) VALUES ("003","Visaka Edirisinghe","visakaaero@gmail.com","0718234869");

INSERT INTO loans (loan_id,book_id,member_id,loan_date,return_date) VALUES ("01","1","002","2024-03-03","2024-05-05");
INSERT INTO loans (loan_id,book_id,member_id,loan_date,return_date) VALUES ("02","2","001","2024-02-01","2024-03-09");





