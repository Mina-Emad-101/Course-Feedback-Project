package com.uni.projectforms.Responses;

/**
 * InstructorRatingsResponse
 */
public class InstructorRatingsResponse {

	private Double averageInstructorRating;
	private Long responseCount;
	
	public InstructorRatingsResponse(Double averageInstructorRating, Long responseCount) {
		this.averageInstructorRating = averageInstructorRating;
		this.responseCount = responseCount;
	}

	public Double getAverageInstructorRating() {
		return averageInstructorRating;
	}

	public Long getResponseCount() {
		return responseCount;
	}
}
