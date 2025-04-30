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
import com.uni.project.Models.Role;
import com.uni.project.Models.Dtos.ModelDTO;
import com.uni.project.Models.Dtos.RoleBasicDTO;
import com.uni.project.Models.Dtos.RoleWithUsersDTO;
import com.uni.project.Repositories.RoleRepository;
import com.uni.project.Responses.ErrorResponse;

/**
 * RoleController
 */
@RestController
public class RoleController {

	private RoleRepository roleRepository;

	RoleController(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@GetMapping("/roles")
	@AuthGuarded
	public ResponseEntity<Object> getRoles() {
		List<ModelDTO> roles = new ArrayList<ModelDTO>();
		this.roleRepository.findAll().forEach((Role role) -> roles.add(new RoleBasicDTO(role)));
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/roles/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getRole(@PathVariable Long id) {
		Optional<Role> role = this.roleRepository.findById(id);

		if (role.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}
		ModelDTO roleDto = new RoleWithUsersDTO(role.get());
		return ResponseEntity.ok(roleDto);
	}
}
