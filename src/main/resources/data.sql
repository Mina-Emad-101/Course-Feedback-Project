INSERT INTO roles (name)
VALUES
    ('Admin'),
    ('Instructor'),
    ('Student');

INSERT INTO users (name, email, password, role_id)
VALUES
    ('Admin', 'admin@gmail.com', "^�H��(qQ��o��)'s`=j���*�rB�", 1),
    ('Instructor', 'instructor@gmail.com', "^�H��(qQ��o��)'s`=j���*�rB�", 2),
    ('Student', 'student@gmail.com', "^�H��(qQ��o��)'s`=j���*�rB�", 3);
