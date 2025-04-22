-- Drop tables if they exist (optional for dev environments)
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

-- Create authors table
CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create books table
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);
