package com.uni.project.Responses;

import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * ValidationErrorResponse
 */
public class ValidationErrorResponse {

	private List<String> errors;

	public ValidationErrorResponse(MethodArgumentNotValidException validationException) {
		List<String> errors = validationException.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();

		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
}
