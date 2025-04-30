package com.uni.project.Models.Dtos;

import com.uni.project.Models.Role;

import lombok.Data;

/**
 * RoleBasicDTO
 */
@Data
public class RoleBasicDTO implements ModelDTO {

	private Long id;
	private String name;

	public RoleBasicDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
	}
}
