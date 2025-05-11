package com.uni.projectforms.Requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

/**
 * UpdateFormRequest
 */
public class UpdateFormRequest {

	@NotNull
	private LocalDate deadline;

	@NotNull
	private Boolean isActive;

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
}
