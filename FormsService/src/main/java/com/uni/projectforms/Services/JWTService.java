package com.uni.projectforms.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.ErrorResponse;

import jakarta.annotation.PostConstruct;

/**
 * JWTService
 */
@Service
public class JWTService {

	@Value("${user-service-base-url}")
	private String userServiceUrl;

	private final RestClient.Builder builder;
	private RestClient restClient;

	public JWTService(RestClient.Builder builder) {
		this.builder = builder;
	}

	@PostConstruct
	private void init() {
		System.out.println("Loaded base URL: " + this.userServiceUrl);
		this.restClient = builder.baseUrl(this.userServiceUrl).build();
	}

	public User verify(String token) throws JWTVerificationException {
		User user;
		try {
			ResponseEntity<User> response = this.restClient.post()
					.uri("/auth/verify-token/{token}", token)
					.retrieve()
					.toEntity(User.class);
			user = response.getBody();
		} catch (HttpStatusCodeException e) {
			ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
			throw new JWTVerificationException(errorResponse.getErrorMessage());
		}
		return user;
	}
}
