package com.uni.projectforms.Models.Dtos;

import java.time.LocalDate;

import com.uni.projectforms.Models.Response;

/**
 * ResponseBasicDTO
 */
public class ResponseBasicDTO implements ModelDTO {
	private Long id;
	private String comment;
	private Integer courseRating;
	private Integer instructorRating;
	private Long formID;
	private Long fillerID;

	public ResponseBasicDTO(Response response) {
		this.id = response.getId();
		this.comment = response.getComment();
		this.courseRating = response.getCourseRating();
		this.instructorRating = response.getInstructorRating();
		this.formID = response.getForm().getId();
		this.fillerID = response.getFiller().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getFormID() {
		return formID;
	}

	public void setFormID(Long formID) {
		this.formID = formID;
	}

	public Long getFillerID() {
		return fillerID;
	}

	public void setFillerID(Long fillerID) {
		this.fillerID = fillerID;
	}
}
