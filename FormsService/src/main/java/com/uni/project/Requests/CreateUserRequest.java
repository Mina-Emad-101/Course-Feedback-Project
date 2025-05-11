package com.uni.project.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * CreateUserRequest
 */
public class CreateUserRequest {

	@NotNull
	@Size(min = 3, max = 20)
	private String name;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 8, max = 255)
	private String password;

	@NotNull
	@Positive
	private Long roleID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleID() {
		return roleID;
	}

	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}
}
