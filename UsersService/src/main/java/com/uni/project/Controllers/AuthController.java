package com.uni.project.Controllers;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uni.project.Exceptions.JWTExpiredException;
import com.uni.project.Models.Role;
import com.uni.project.Models.User;
import com.uni.project.Models.Dtos.UserWithRoleDTO;
import com.uni.project.Repositories.RoleRepository;
import com.uni.project.Repositories.UserRepository;
import com.uni.project.Requests.LoginRequest;
import com.uni.project.Requests.RegisterRequest;
import com.uni.project.Responses.ErrorResponse;
import com.uni.project.Responses.LoginResponse;
import com.uni.project.Services.HashService;
import com.uni.project.Services.JWTService;

import jakarta.validation.Valid;

/**
 * AuthController
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HashService hashService;

	@Autowired
	JWTService jwtService;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest)
			throws NoSuchAlgorithmException {
		User user = User.builder()
				.name(registerRequest.getName())
				.email(registerRequest.getEmail())
				.password(hashService.hashString(registerRequest.getPassword()))
				.role(this.roleRepository.findById(Role.getStudentRoleID()).orElseThrow())
				.build();

		User newUser = this.userRepository.save(user);
		return ResponseEntity.ok(new UserWithRoleDTO(newUser));
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) throws NoSuchAlgorithmException {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Email Not Found"));
		}

		if (!hashService.compareHash(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Incorrect Password"));
		}

		String token = this.jwtService.create(user.getId());

		return ResponseEntity.ok(new LoginResponse(token, new UserWithRoleDTO(user)));
	}

	@PostMapping("/verify-token/{token}")
	public ResponseEntity<Object> verifyToken(@PathVariable String token) {
		Long id;
		try {
			id = this.jwtService.verify(token);
		} catch (JWTExpiredException expiredException) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse(expiredException.getMessage()));
		} catch (JWTVerificationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Couldn't Verify Token"));
		}
		User user = this.userRepository.findById(id).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("User Not Found, Please Login Again"));
		}
		return ResponseEntity.ok(new UserWithRoleDTO(user));
	}

}
