package com.uni.project.Dao;

import java.util.List;

import com.uni.project.Models.Book;

/**
 * BookDao
 */
public interface BookDao {

	public BookDao withAuthor();
	public Book getBook(Long id);
	public List<Book> getBooks();
	public void createBook(Book book);
}
