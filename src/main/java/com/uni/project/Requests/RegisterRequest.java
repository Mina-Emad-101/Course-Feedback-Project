package com.uni.project.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * RegisterRequest
 */
public class RegisterRequest {

	@NotNull
	@Size(min = 3, max = 20)
	private String name;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 8, max = 255)
	private String password;

	public RegisterRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return name;
	}
}
