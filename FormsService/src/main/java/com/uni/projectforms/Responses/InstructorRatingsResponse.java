package com.uni.projectforms.Responses;

import java.util.List;

import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Models.Dtos.CourseWithInstructorDTO;

/**
 * InstructorRatingsResponse
 */
public class InstructorRatingsResponse {

	private Double averageInstructorRating;
	private Long responseCount;
	private List<CourseWithInstructorDTO> courses;
	
	public InstructorRatingsResponse(Double averageInstructorRating, Long responseCount, User instructor) {
		this.averageInstructorRating = averageInstructorRating;
		this.responseCount = responseCount;
		this.courses = instructor.getCourses().stream().map((Course course) -> new CourseWithInstructorDTO(course)).toList();
	}

	public Double getAverageInstructorRating() {
		return averageInstructorRating;
	}

	public Long getResponseCount() {
		return responseCount;
	}

	public List<CourseWithInstructorDTO> getCourses() {
		return courses;
	}
}
