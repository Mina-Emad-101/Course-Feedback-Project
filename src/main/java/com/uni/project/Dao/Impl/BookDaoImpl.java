package com.uni.project.Dao.Impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.uni.project.Dao.BookDao;
import com.uni.project.Models.Author;
import com.uni.project.Models.Book;

/**
 * BookDaoImpl
 */
@Component
public class BookDaoImpl implements BookDao {

	private final JdbcTemplate jdbcTemplate;
	private boolean withAuthor = false;

	public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public BookDao withAuthor() {
		this.withAuthor = true;
		return this;
	}

	@Override
	public Book getBook(Long id) {
		String sql = "SELECT * FROM books";
		if (this.withAuthor) {
			sql = sql.concat(" LEFT JOIN authors ON books.author_id = authors.id");
		}
		sql = sql.concat(String.format(" WHERE books.id = %d;", id));

		List<Book> books = this.jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Book.BookBuilder builder = Book.builder()
					.id(rs.getLong("books.id"))
					.title(rs.getString("books.title"))
					.author_id(rs.getLong("books.author_id"))
					.created_at(rs.getTimestamp("books.created_at"));

			if (this.withAuthor) {
				builder = builder.author(
						Author.builder()
								.id(rs.getLong("authors.id"))
								.name(rs.getString("authors.name"))
								.bio(rs.getString("authors.bio"))
								.created_at(rs.getTimestamp("authors.created_at"))
								.build());
			}
			Book book = builder.build();
			return book;
		});
		this.withAuthor = false;
		return books.getFirst();
	}

	@Override
	public List<Book> getBooks() {
		String sql = "SELECT * FROM books";
		if (this.withAuthor) {
			sql = sql.concat(" LEFT JOIN authors ON books.author_id = authors.id");
		}

		List<Book> books = this.jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Book.BookBuilder builder = Book.builder()
					.id(rs.getLong("books.id"))
					.title(rs.getString("books.title"))
					.author_id(rs.getLong("books.author_id"))
					.created_at(rs.getTimestamp("books.created_at"));

			if (this.withAuthor) {
				builder = builder.author(
						Author.builder()
								.id(rs.getLong("authors.id"))
								.name(rs.getString("authors.name"))
								.bio(rs.getString("authors.bio"))
								.created_at(rs.getTimestamp("authors.created_at"))
								.build());
			}
			Book book = builder.build();
			return book;
		});
		this.withAuthor = false;
		return books;
	}


	@Override
	public void createBook(Book book) {
		this.jdbcTemplate.update("INSERT INTO books (title, author_id) VALUES (?, ?)", book.getTitle(), book.getAuthor_id());
		return;
	}
}
