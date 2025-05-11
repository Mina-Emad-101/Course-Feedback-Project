package com.uni.project.Models.Dtos;

import java.sql.Timestamp;

import com.uni.project.Models.User;

import lombok.Data;

/**
 * UserBasicDTO
 */
@Data
public class UserBasicDTO implements ModelDTO {

	private Long id;
	private String name;
	private String email;
	private String role;
	private Timestamp created_at;

	public UserBasicDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRole().getName();
		this.created_at = user.getCreated_at();
	}
}
