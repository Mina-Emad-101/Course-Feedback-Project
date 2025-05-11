package com.uni.project.Requests;

import jakarta.validation.constraints.NotNull;

/**
 * LoginRequest
 */
public class LoginRequest {

	@NotNull
	private String email;

	@NotNull
	private String password;

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
