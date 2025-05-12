package com.uni.projectforms.Responses;

/**
 * CourseRatingsResponse
 */
public class CourseRatingsResponse {

	private Double averageCourseRating;
	private Long responseCount;
	
	public CourseRatingsResponse(Double averageCourseRating, Long responseCount) {
		this.averageCourseRating = averageCourseRating;
		this.responseCount = responseCount;
	}

	public Double getAverageCourseRating() {
		return averageCourseRating;
	}

	public Long getResponseCount() {
		return responseCount;
	}
}
