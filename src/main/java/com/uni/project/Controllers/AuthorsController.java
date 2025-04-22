package com.uni.project.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uni.project.Models.Author;
import com.uni.project.Services.AuthorService;

/**
 * AuthorsController
 */
@RestController
public class AuthorsController {

	private AuthorService authorService;

	public AuthorsController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/authors/{id}")
	public Author getAuthor(@PathVariable Long id) {
		return this.authorService.getAuthor(id);
	}

	@GetMapping("/authors")
	public List<Author> getAuthors() {
		return this.authorService.getAuthors();
	}
}
