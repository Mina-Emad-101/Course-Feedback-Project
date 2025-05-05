INSERT INTO roles (name)
VALUES
    ('Admin'),
    ('Instructor'),
    ('Student');

INSERT INTO users (name, email, password, role_id)
VALUES
    ('Admin', 'admin@gmail.com', '$2y$12$sVi5nffI3a/dgdY7ICq.re5kOG80ovoSE9LVnM0z9rPQXaFJDH53e', 1),
    ('Instructor', 'instructor@gmail.com', '$2y$12$sVi5nffI3a/dgdY7ICq.re5kOG80ovoSE9LVnM0z9rPQXaFJDH53e', 2),
    ('Student', 'student@gmail.com', '$2y$12$sVi5nffI3a/dgdY7ICq.re5kOG80ovoSE9LVnM0z9rPQXaFJDH53e', 3);
