package com.uni.project.Models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

	private Long id;
	private String name;
	private String bio;
	private Timestamp created_at;
}
