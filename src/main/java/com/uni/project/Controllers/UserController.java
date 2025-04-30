package com.uni.project.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.uni.project.Annotations.AuthGuarded;
import com.uni.project.Models.User;
import com.uni.project.Models.Dtos.ModelDTO;
import com.uni.project.Models.Dtos.UserBasicDTO;
import com.uni.project.Models.Dtos.UserWithRoleDTO;
import com.uni.project.Repositories.RoleRepository;
import com.uni.project.Repositories.UserRepository;
import com.uni.project.Responses.ErrorResponse;

/**
 * UserController
 */
@RestController
public class UserController {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	UserController(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

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

}
