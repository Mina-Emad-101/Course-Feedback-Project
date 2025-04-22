package com.uni.project.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uni.project.Dao.AuthorDao;
import com.uni.project.Dao.BookDao;
import com.uni.project.Models.Book;
import com.uni.project.Services.BookService;

/**
 * BookServiceImpl
 */
@Service
public class BookServiceImpl implements BookService {

	private BookDao bookDao;
	private boolean withAuthor = false;

	public BookServiceImpl(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public BookService withAuthor() {
		this.withAuthor = true;
		return this;
	}

	@Override
	public Book getBook(Long id) {
		if (this.withAuthor) {
			this.bookDao = this.bookDao.withAuthor();
			this.withAuthor = false;
		}
		Book book = this.bookDao.getBook(id);
		return book;
	}

	@Override
	public List<Book> getBooks() {
		if (this.withAuthor) {
			this.bookDao = this.bookDao.withAuthor();
			this.withAuthor = false;
		}
		List<Book> books = this.bookDao.getBooks();
		return books;
	}

	@Override
	public void createBook(Book book) {
		this.bookDao.createBook(book);
	}
}
