package com.uni.projectforms.Responses;

/**
 * FormResultsResponse
 */
public class FormResultsResponse {

	private Double averageCourseRating;
	private Double averageInstructorRating;
	private Long responseCount;
	
	public FormResultsResponse(Double averageCourseRating, Double averageInstructorRating, Long responseCount) {
		this.averageCourseRating = averageCourseRating;
		this.averageInstructorRating = averageInstructorRating;
		this.responseCount = responseCount;
	}

	public Double getAverageCourseRating() {
		return averageCourseRating;
	}

	public Double getAverageInstructorRating() {
		return averageInstructorRating;
	}

	public Long getResponseCount() {
		return responseCount;
	}
}
