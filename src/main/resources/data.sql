INSERT INTO roles (name)
VALUES
    ('Admin'),
    ('Instructor'),
    ('Student');

INSERT INTO users (name, email, password, role_id)
VALUES
    ('Admin', 'admin@gmail.com', 'password', 1),
    ('Instructor', 'instructor@gmail.com', 'password', 2),
    ('Student', 'student@gmail.com', 'password', 3);
