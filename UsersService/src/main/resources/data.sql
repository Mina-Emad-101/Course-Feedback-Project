INSERT INTO roles (name)
SELECT * FROM (
    SELECT 'Admin' AS name
    UNION ALL
    SELECT 'Instructor'
    UNION ALL
    SELECT 'Student'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM roles);

INSERT INTO users (name, email, password, role_id)
SELECT * FROM (
    SELECT 'Admin', 'admin@gmail.com', '$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6', 1
    UNION ALL
    SELECT 'Instructor', 'instructor@gmail.com', '$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6', 2
    UNION ALL
    SELECT 'Student', 'student@gmail.com', '$2a$10$ut7KNKwhcjvU6EdbHWT9gue85nU99h5UwBLNCVwTeaH5f/ZrG53M6', 3
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users);
