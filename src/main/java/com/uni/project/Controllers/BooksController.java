package com.uni.project.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uni.project.Models.Book;
import com.uni.project.Services.BookService;

/**
 * BooksController
 */
@RestController
public class BooksController {

	private BookService bookService;

	public BooksController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable Long id, @RequestParam Optional<Boolean> withAuthor) {
		if (withAuthor.isPresent() && withAuthor.get().booleanValue()) {
			this.bookService = this.bookService.withAuthor();
		}
		Book book = this.bookService.getBook(id);
		return book;
	}

	@GetMapping("/books")
	public List<Book> getBooks(@RequestParam Optional<Boolean> withAuthor) {
		if (withAuthor.isPresent() && withAuthor.get().booleanValue()) {
			this.bookService = this.bookService.withAuthor();
		}
		List<Book> books = this.bookService.getBooks();
		return books;
	}

	@PostMapping("/books")
	public String createBook(@RequestBody Book book) {
		try {
			this.bookService.createBook(book);
			return "Saved Successfully";
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
}
