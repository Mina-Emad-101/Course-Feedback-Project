package com.uni.projectforms.Models.Dtos;

import com.uni.projectforms.Models.Course;

/**
 * CourseBasicDTO
 */
public class CourseBasicDTO implements ModelDTO {
	private Long id;
	private String name;
	private String code;
	private Long instructorID;

	public CourseBasicDTO(Course course) {
		this.id = course.getId();
		this.name = course.getName();
		this.code = course.getCode();
		this.instructorID = course.getInstructor().getId();
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
}
