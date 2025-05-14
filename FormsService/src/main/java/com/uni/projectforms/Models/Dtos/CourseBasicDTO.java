package com.uni.projectforms.Models.Dtos;

import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.Form;
import com.uni.projectforms.Models.Response;

/**
 * CourseBasicDTO
 */
public class CourseBasicDTO implements ModelDTO {
	private Long id;
	private String name;
	private String code;
	private Long instructorID;
	private Long responseCount;
	private Double averageRating;

	public CourseBasicDTO(Course course) {
		this.id = course.getId();
		this.name = course.getName();
		this.code = course.getCode();
		this.instructorID = course.getInstructor().getId();
		Integer ratingSum = course.getForms().stream()
				.map((Form form) -> form.getResponses().stream()
						.map((Response response) -> response.getCourseRating())
						.reduce(0, Integer::sum)) // sum ratings in one form
				.reduce(Integer::sum).orElse(0);
		this.responseCount = (long) course.getForms().stream().map((Form form) -> form.getResponses().size()).reduce(0,
				Integer::sum);
		this.averageRating = this.responseCount == 0 ? 0.00 : (double) ratingSum / (double) this.responseCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(Long instructorID) {
		this.instructorID = instructorID;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Long getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(Long responseCount) {
		this.responseCount = responseCount;
	}
}
