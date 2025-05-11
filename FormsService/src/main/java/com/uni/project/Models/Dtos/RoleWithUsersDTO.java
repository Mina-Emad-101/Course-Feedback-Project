package com.uni.project.Models.Dtos;

import java.util.List;

import com.uni.project.Models.Role;
import com.uni.project.Models.User;

import lombok.Data;

/**
 * RoleWithUsersDTO
 */
@Data
public class RoleWithUsersDTO implements ModelDTO {

	private Long id;
	private String name;
	private List<UserBasicDTO> users;

	public RoleWithUsersDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.users = role.getUsers().stream().map((User user) -> new UserBasicDTO(user)).toList();
	}
}
