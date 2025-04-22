package com.uni.project.Models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

	private Long id;
	private String title;
	private Long author_id;
	private Author author;
	private Timestamp created_at;
}
