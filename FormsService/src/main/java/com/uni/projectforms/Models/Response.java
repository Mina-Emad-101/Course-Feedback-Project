package com.uni.projectforms.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course
 */
@Entity
@Table(name = "feedback_responses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;

	@Column(name = "course_rating", nullable = false)
	private Integer courseRating;
	@Column(name = "instructor_rating", nullable = false)
	private Integer instructorRating;

	@ManyToOne
	@JoinColumn(name = "form_id", nullable = false)
	private Form form;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private User student;
}
