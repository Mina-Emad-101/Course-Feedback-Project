package com.uni.projectforms.Models.Dtos;

import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.Form;
import com.uni.projectforms.Models.Response;

/**
 * CourseWithInstructorDTO
 */
public class CourseWithInstructorDTO implements ModelDTO {
	private Long id;
	private String name;
	private String code;
	private ModelDTO instructor;
	private Long responseCount;
	private Double averageRating;

	public CourseWithInstructorDTO(Course course) {
		this.id = course.getId();
		this.name = course.getName();
		this.code = course.getCode();
		this.instructor = new UserWithRoleDTO(course.getInstructor());
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

	public ModelDTO getInstructor() {
		return instructor;
	}

	public void setInstructor(ModelDTO instructor) {
		this.instructor = instructor;
	}

	public Long getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(Long responseCount) {
		this.responseCount = responseCount;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
}
