package com.uni.project.Dao.Impl;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.uni.project.Dao.AuthorDao;
import com.uni.project.Models.Author;

/**
 * AuthorDaoImpl
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

	private final JdbcTemplate jdbcTemplate;

	public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Author getAuthor(Long id) {
		List<Author> authors = this.jdbcTemplate.query(String.format("SELECT * FROM authors WHERE id = %d", id), (ResultSet rs, int rowNum) -> {
			return Author.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.bio(rs.getString("bio"))
				.created_at(rs.getTimestamp("created_at"))
				.build();
		});
		return authors.getFirst();
	}

	@Override
	public List<Author> getAuthors() {
		List<Author> authors = this.jdbcTemplate.query("SELECT * FROM authors", (ResultSet rs, int rowNum) -> {
			return Author.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.bio(rs.getString("bio"))
				.created_at(rs.getTimestamp("created_at"))
				.build();
		});
		return authors;
	}
}
