package com.uni.project.Requests;

/**
 * LoginRequest
 */
public class LoginRequest {

	private String email;
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
