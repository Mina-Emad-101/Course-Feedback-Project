INSERT INTO roles (name)
VALUES
    ('Admin'),
    ('Instructor'),
    ('Student');

INSERT INTO users (name, email, password, role_id)
VALUES
    ('Admin', 'admin@gmail.com', "$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6", 1),
    ('Instructor', 'instructor@gmail.com', "$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6", 2),
    ('Student', 'student@gmail.com', "$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6", 3);
