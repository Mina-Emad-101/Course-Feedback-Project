DROP TABLE IF EXISTS feedback_responses;
DROP TABLE IF EXISTS feedback_forms;
DROP TABLE IF EXISTS courses;

-- Create courses table
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	code VARCHAR(255) NOT NULL,
    instructor_id BIGINT NOT NULL,
    CONSTRAINT fk_instructor FOREIGN KEY (instructor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create forms table
CREATE TABLE feedback_forms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	course_id BIGINT NOT NULL,
	created_by BIGINT NOT NULL,
    deadline DATE,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Create responses table
CREATE TABLE feedback_responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	filled_by BIGINT NOT NULL,
	form_id BIGINT NOT NULL,
	course_rating TINYINT NOT NULL,
	instructor_rating TINYINT NOT NULL,
	comment VARCHAR(255) NOT NULL,
    CONSTRAINT fk_form FOREIGN KEY (form_id) REFERENCES feedback_forms(id) ON DELETE CASCADE,
    CONSTRAINT fk_filled_by FOREIGN KEY (filled_by) REFERENCES users(id) ON DELETE CASCADE
);
