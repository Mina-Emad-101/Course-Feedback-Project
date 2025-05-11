package com.uni.projectforms.Requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * CreateFormRequest
 */
public class CreateFormRequest {

	@NotNull
	private LocalDate deadline;

	@NotNull
	@Positive
	private Long courseID;

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Long getCourseID() {
		return courseID;
	}

	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}
}
