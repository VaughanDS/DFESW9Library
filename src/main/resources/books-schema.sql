drop table if exists `books` CASCADE;
CREATE TABLE books (
    bookId BIGINT AUTO_INCREMENT,
    bookName VARCHAR(255),
    authorName VARCHAR(255),
    PRIMARY KEY (bookId)
);