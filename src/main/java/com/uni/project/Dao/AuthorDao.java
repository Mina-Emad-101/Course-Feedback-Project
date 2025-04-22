package com.uni.project.Dao;

import java.util.List;

import com.uni.project.Models.Author;

/**
 * AuthorDao
 */
public interface AuthorDao {

	public Author getAuthor(Long id);
	public List<Author> getAuthors();
}
