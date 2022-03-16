package com.qa.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Long bookId;
	@Column(name = "bookName")
	private String bookName;
	@Column(name = "authorName")
	private String authorName;

	public Book() {
		super();
	}

	public Book(String bookName, String authorName) {
		this.bookName = bookName;
		this.authorName = authorName;

	}

	public Book(Long bookId, String bookName, String authorName) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.authorName = authorName;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", authorName=" + authorName + "]";
	}

}
