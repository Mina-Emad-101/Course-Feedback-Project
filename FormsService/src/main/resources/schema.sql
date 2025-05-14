-- Create courses table
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	code VARCHAR(255) NOT NULL UNIQUE,
    instructor_id BIGINT NOT NULL,
    CONSTRAINT fk_instructor FOREIGN KEY (instructor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create forms table
CREATE TABLE IF NOT EXISTS feedback_forms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	course_id BIGINT NOT NULL,
	created_by BIGINT NOT NULL,
    deadline DATE,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Create responses table
CREATE TABLE IF NOT EXISTS feedback_responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	student_id BIGINT NOT NULL,
	form_id BIGINT NOT NULL,
	course_rating TINYINT NOT NULL,
	instructor_rating TINYINT NOT NULL,
	comment VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_form FOREIGN KEY (form_id) REFERENCES feedback_forms(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);
