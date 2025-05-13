package com.uni.projectforms.Models.Dtos;

import com.uni.projectforms.Models.Response;

/**
 * ResponseBasicDTO
 */
public class ResponseBasicDTO implements ModelDTO {
	private Long id;
	private String comment;
	private Integer courseRating;
	private Integer instructorRating;
	private ModelDTO form;

	public ResponseBasicDTO(Response response) {
		this.id = response.getId();
		this.comment = response.getComment();
		this.courseRating = response.getCourseRating();
		this.instructorRating = response.getInstructorRating();
		this.form = new FormBasicDTO(response.getForm());
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

	public ModelDTO getForm() {
		return form;
	}

	public void setForm(ModelDTO form) {
		this.form = form;
	}
}
