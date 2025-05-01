package com.uni.project.Controllers;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.uni.project.Annotations.AuthGuarded;
import com.uni.project.Models.Role;
import com.uni.project.Models.User;
import com.uni.project.Models.Dtos.ModelDTO;
import com.uni.project.Models.Dtos.UserBasicDTO;
import com.uni.project.Models.Dtos.UserWithRoleDTO;
import com.uni.project.Repositories.RoleRepository;
import com.uni.project.Repositories.UserRepository;
import com.uni.project.Requests.CreateUserRequest;
import com.uni.project.Requests.UpdateUserRequest;
import com.uni.project.Responses.ErrorResponse;
import com.uni.project.Services.HashService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * UserController
 */
@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private HashService hashService;

	@GetMapping("/users")
	@AuthGuarded
	public ResponseEntity<Object> getUsers() {
		Iterable<User> users = this.userRepository.findAll();
		List<ModelDTO> usersDTO = new ArrayList<ModelDTO>();

		for (User user : users) {
			usersDTO.add(new UserBasicDTO(user));
		}
		return ResponseEntity.ok(usersDTO);
	}

	@GetMapping("/users/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		Optional<User> user = this.userRepository.findById(id);

		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}
		ModelDTO userDto = new UserWithRoleDTO(user.get());
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/users")
	@AuthGuarded
	public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest userRequest) throws NoSuchAlgorithmException {
		String password = userRequest.getPassword();
		String hashedPassword = this.hashService.hashString(password);

		Role role = this.roleRepository.findById(userRequest.getRoleID()).orElse(null);
		if (role == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Role ID not found"));
		}

		User user = User.builder()
		.name(userRequest.getName())
		.email(userRequest.getEmail())
		.password(hashedPassword)
		.role(role)
		.build();

		User newUser = this.userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(new UserWithRoleDTO(newUser));
	}

	@PutMapping("/users/{id}")
	@AuthGuarded
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest userRequest) throws NoSuchAlgorithmException {
		User originalUser = this.userRepository.findById(id).orElse(null);
		if (originalUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User Not Found"));
		}

		Role role = this.roleRepository.findById(userRequest.getRoleID()).orElse(null);
		if (role == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Role ID not found"));
		}

		String password = userRequest.getPassword();
		String hashedPassword = this.hashService.hashString(password);

		originalUser.setName(userRequest.getName());
		originalUser.setEmail(userRequest.getEmail());
		originalUser.setPassword(hashedPassword);
		originalUser.setRole(role);

		User updatedUser = this.userRepository.save(originalUser);

		return ResponseEntity.ok(new UserWithRoleDTO(updatedUser));
	}
}
