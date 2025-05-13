package com.uni.projectforms.Models.Dtos;

import java.sql.Timestamp;

import com.uni.projectforms.Models.Role;
import com.uni.projectforms.Models.User;

import lombok.Data;

/**
 * UserWithRoleDTO
 */
@Data
public class UserWithRoleDTO implements ModelDTO {

	private Long id;
	private String name;
	private String email;
	private Role role;
	private Timestamp created_at;

	public UserWithRoleDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.created_at = user.getCreated_at();
	}
}
