INSERT INTO courses (name, code, instructor_id)
SELECT * FROM (
    SELECT 'Course 1' AS name, 'CS1' AS code, 2 AS instructor_id
    UNION ALL
    SELECT 'Course 2', 'CS2', 2
    UNION ALL
    SELECT 'Course 3', 'CS3', 2
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM courses);
