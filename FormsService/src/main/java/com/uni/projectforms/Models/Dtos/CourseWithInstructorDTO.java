package com.uni.projectforms.Models.Dtos;

import com.uni.projectforms.Models.Course;

/**
 * CourseWithInstructorDTO
 */
public class CourseWithInstructorDTO implements ModelDTO {
	private Long id;
	private String name;
	private String code;
	private ModelDTO instructor;

	public CourseWithInstructorDTO(Course course) {
		this.id = course.getId();
		this.name = course.getName();
		this.code = course.getCode();
		this.instructor = new UserWithRoleDTO(course.getInstructor());
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
}
