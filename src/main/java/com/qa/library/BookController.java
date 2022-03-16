package com.qa.library;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.library.domain.Book;
import com.qa.library.service.BookService;

@RestController
public class BookController {
	private List<Book> books = new ArrayList<>();
	private BookService service;

	public BookController(BookService service) {
		super();
		this.service = service;
	}

	@PostMapping("/createBook")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		return new ResponseEntity<>(this.service.create(book), HttpStatus.CREATED);
	}

	@GetMapping("/getBooks")
	public ResponseEntity<List<Book>> readAll() {
		return new ResponseEntity<List<Book>>(this.service.readAll(), HttpStatus.FOUND);
	}

	@GetMapping("/getById/{bookId}")
	public ResponseEntity<Book> readById(@PathVariable Long bookId) {
		return new ResponseEntity<Book>(this.service.readOne(bookId), HttpStatus.FOUND);
	}

	@PutMapping("/update/{bookId}")
	public ResponseEntity<Book> update(@PathVariable Long bookId, @RequestBody Book book) {
		return new ResponseEntity<Book>(this.service.update(bookId, book), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/removeBook/{bookId}")
	public ResponseEntity<?> delete(@PathVariable Long bookId) {
		return (this.service.delete(bookId)) != null ? new ResponseEntity<Boolean>(HttpStatus.OK)
				: new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
	}

}
