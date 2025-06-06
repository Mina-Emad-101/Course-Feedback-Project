package com.uni.projectforms.Models.Dtos;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.uni.projectforms.Models.Form;

/**
 * FormBasicDTO
 */
public class FormBasicDTO implements ModelDTO {
	private Long id;
	private LocalDate deadline;
	private Boolean isActive;
	private ModelDTO course;
	private ModelDTO creator;
	private Timestamp createdAt;
	private Integer responseCount;

	public FormBasicDTO(Form form) {
		this.id = form.getId();
		this.deadline = form.getDeadline();
		this.isActive = form.getIsActive();
		this.course = new CourseWithInstructorDTO(form.getCourse());
		this.creator = new UserWithRoleDTO(form.getCreator());
		this.createdAt = form.getCreatedAt();
		this.responseCount = form.getResponses().size();
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

	public ModelDTO getCourse() {
		return course;
	}

	public void setCourse(ModelDTO course) {
		this.course = course;
	}

	public ModelDTO getCreator() {
		return creator;
	}

	public void setCreator(ModelDTO creator) {
		this.creator = creator;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getResponseCount() {
		return responseCount;
	}
}
