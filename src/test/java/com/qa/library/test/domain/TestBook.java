package com.qa.library.test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.qa.library.domain.Book;

public class TestBook {

//	@Test
//	public void equalsContract() {
//		EqualsVerifier.forClass(Book.class).verify();
//	}

	@Test
	public void testConstructorWithId() {
		Book book = new Book(1L, "TestBook", "TestAuthor");
		assertNotNull(book.getBookId());
		assertNotNull(book.getBookName());
		assertNotNull(book.getAuthorName());

		assertEquals((Long) 1L, book.getBookId());
		assertEquals("TestBook", book.getBookName());
		assertEquals("TestAuthor", book.getAuthorName());
	}

	@Test
	public void testToString() {
		Book book = new Book(1L, "TestBookName", "TestAuthorName");
		assertEquals("Book [bookId=1, bookName=TestBookName, authorName=TestAuthorName]", book.toString());
	}
}
