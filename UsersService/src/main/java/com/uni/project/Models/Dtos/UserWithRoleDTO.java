package com.uni.project.Models.Dtos;

import java.sql.Timestamp;

import com.uni.project.Models.Role;
import com.uni.project.Models.User;

import lombok.Data;

/**
 * UserWithRoleDTO
 */
@Data
public class UserWithRoleDTO implements ModelDTO {

	private Long id;
	private String name;
	private String email;
	private ModelDTO role;
	private Timestamp created_at;

	public UserWithRoleDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = new RoleBasicDTO(user.getRole());
		this.created_at = user.getCreated_at();
	}
}
