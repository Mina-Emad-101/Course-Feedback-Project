package com.uni.project.Services;

import java.util.List;

import com.uni.project.Models.Author;

/**
 * AuthorService
 */
public interface AuthorService {

	public Author getAuthor(Long id);
	public List<Author> getAuthors();
}
