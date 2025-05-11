package com.uni.project.Responses;

import com.uni.project.Models.User;
import com.uni.project.Models.Dtos.ModelDTO;

/**
 * LoginResponse
 */
public class LoginResponse {

	private String token;
	private ModelDTO user;

	public String getToken() {
		return token;
	}

	public ModelDTO getUser() {
		return user;
	}

	public LoginResponse(String token, ModelDTO user) {
		this.token = token;
		this.user = user;
	}
}
