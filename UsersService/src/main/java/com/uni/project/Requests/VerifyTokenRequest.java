package com.uni.project.Requests;

import jakarta.validation.constraints.NotNull;

/**
 * VerifyTokenRequest
 */
public class VerifyTokenRequest {

	@NotNull
	private String token;

	public String getToken() {
		return this.token;
	}

	public VerifyTokenRequest(String token) {
		this.token = token;
	}
}
