package com.uni.projectforms.Models.Dtos;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.uni.projectforms.Models.Form;

/**
 * FormBasicDTO
 */
public class FormEmptyDTO implements ModelDTO {
	private Long id;
	private LocalDate deadline;
	private Boolean isActive;
	private Timestamp createdAt;

	public FormEmptyDTO(Form form) {
		this.id = form.getId();
		this.deadline = form.getDeadline();
		this.isActive = form.getIsActive();
		this.createdAt = form.getCreatedAt();
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
