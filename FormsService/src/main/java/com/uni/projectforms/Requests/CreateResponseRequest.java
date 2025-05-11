package com.uni.projectforms.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * CreateResponseRequest
 */
public class CreateResponseRequest {

	@NotNull
	@Size(min = 3, max = 255)
	private String comment;

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private Integer courseRating;

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private Integer instructorRating;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCourseRating() {
		return courseRating;
	}

	public void setCourseRating(Integer courseRating) {
		this.courseRating = courseRating;
	}

	public Integer getInstructorRating() {
		return instructorRating;
	}

	public void setInstructorRating(Integer instructorRating) {
		this.instructorRating = instructorRating;
	}

}
