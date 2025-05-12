package com.uni.projectforms.Repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.ErrorResponse;

import jakarta.annotation.PostConstruct;


/**
 * UserRepository
 */
@Repository
public class UserRepository {
	@Value("${user-service-base-url}")
	private String userServiceUrl;

	private final RestClient.Builder builder;
	private RestClient restClient;

	public UserRepository(RestClient.Builder builder) {
		this.builder = builder;
	}

	@PostConstruct
	private void init() {
		System.out.println("Loaded User Service BaseURL: " + this.userServiceUrl);
		this.restClient = builder.baseUrl(this.userServiceUrl).build();
	}

	public Optional<User> findById(Long id) {
		User user;
		try {
			String token = "Bearer " + User.getBearerToken().get();
			ResponseEntity<User> response = this.restClient.get()
					.uri("/users/{id}", id)
					.header("Authorization", token)
					.retrieve()
					.toEntity(User.class);
			user = response.getBody();
		} catch (HttpStatusCodeException e) {
			ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
			System.out.println(errorResponse.getErrorMessage());
			return Optional.empty();
		}
		return Optional.of(user);
	}

}
