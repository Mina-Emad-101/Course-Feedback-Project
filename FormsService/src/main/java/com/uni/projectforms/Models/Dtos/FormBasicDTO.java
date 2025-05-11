package com.uni.projectforms.Models.Dtos;

import java.time.LocalDate;

import com.uni.projectforms.Models.Form;

/**
 * FormBasicDTO
 */
public class FormBasicDTO implements ModelDTO {
	private Long id;
	private LocalDate deadline;
	private Boolean isActive;
	private Long courseID;
	private Long creatorID;

	public FormBasicDTO(Form form) {
		this.id = form.getId();
		this.deadline = form.getDeadline();
		this.isActive = form.getIsActive();
		this.courseID = form.getCourse().getId();
		this.creatorID = form.getCreator().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getCourseID() {
		return courseID;
	}

	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}

	public Long getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(Long creatorID) {
		this.creatorID = creatorID;
	}
}
