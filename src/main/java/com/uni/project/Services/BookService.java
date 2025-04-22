package com.uni.project.Services;

import java.util.List;

import com.uni.project.Models.Book;

/**
 * BookService
 */
public interface BookService {

	public BookService withAuthor();
	public Book getBook(Long id);
	public List<Book> getBooks();
	public void createBook(Book book);
}
