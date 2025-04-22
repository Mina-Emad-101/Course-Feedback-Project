package com.uni.project.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uni.project.Dao.AuthorDao;
import com.uni.project.Models.Author;
import com.uni.project.Services.AuthorService;

/**
 * AuthorServiceImpl
 */
@Service
public class AuthorServiceImpl implements AuthorService {

	private AuthorDao authorDao;

	public AuthorServiceImpl(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	@Override
	public Author getAuthor(Long id) {
		return this.authorDao.getAuthor(id);
	}

	@Override
	public List<Author> getAuthors() {
		return this.authorDao.getAuthors();
	}
}
