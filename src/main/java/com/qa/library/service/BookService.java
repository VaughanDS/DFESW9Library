package com.qa.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.library.domain.Book;
import com.qa.library.repo.BookRepository;

@Service
public class BookService {

	private BookRepository bookrepo;

	public BookService(BookRepository bookrepo) {
		super();
		this.bookrepo = bookrepo;
	}

	public Book create(Book createI) {
		return this.bookrepo.save(createI);
	}

	public List<Book> readAll() {
		return this.bookrepo.findAll();
	}

	public Book readOne(Long bookId) {
		Optional<Book> optionalBook = this.bookrepo.findById(bookId);
		return optionalBook.orElse(null);
	}

	public Book update(Long bookId, Book updateI) {
		Optional<Book> toFind = this.bookrepo.findById(bookId);
		Book found = toFind.get();
		found.setBookName(updateI.getBookName());
		found.setAuthorName(updateI.getAuthorName());
		return this.bookrepo.save(found);
	}

	public Book delete(Long bookId) {
		Optional<Book> toDelete = this.bookrepo.findById(bookId);
		this.bookrepo.deleteById(bookId);
		return toDelete.orElse(null);
	}

}