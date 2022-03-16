package com.qa.library.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.library.domain.Book;
import com.qa.library.repo.BookRepository;
import com.qa.library.service.BookService;

@SpringBootTest
@ActiveProfiles("test")
public class TestBookService {

	private Book newBook;
	private Book savedBook;

	@Autowired
	private BookService service;

	@MockBean
	private BookRepository repo;

	@BeforeEach
	void setUp() {
		newBook = new Book("TestBookName", "TestAuthorName");
		savedBook = new Book(1L, "TestBookName", "TestAuthorName");
	}

	@Test
	void testCreate() {

		Mockito.when(this.repo.save(newBook)).thenReturn(savedBook);
		assertThat(this.service.create(newBook)).isEqualTo(savedBook);
		Mockito.verify(this.repo, Mockito.times(1)).save(newBook);
	}

	@Test
	void testReadAll() {
		List<Book> mockInput = new ArrayList<Book>();
		Book book1 = new Book(1L, "TestBookName", "TestAuthorName");
		mockInput.add(book1);
		Book book2 = new Book(2L, "Test2", "ATest2");
		mockInput.add(book2);
		this.repo.save(book1);
		this.repo.save(book2);
		Mockito.when(this.repo.findAll()).thenReturn(mockInput);
		assertEquals(mockInput, this.service.readAll());
		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}

	@Test
	public void testId() throws Exception {
		Book testBook = new Book(0L, "TBN", "TAN");
		Optional<Book> test = Optional.of(testBook);
		Mockito.when(this.repo.findById(Mockito.anyLong())).thenReturn(test);
		Book result = null;
		result = service.readOne(1L);
		Assertions.assertThat(result).isEqualTo(testBook);
		Mockito.verify(repo, Mockito.times(1)).findById(1L);
	}

	@Test
	public void test2Update() {
		Book testBook = new Book(1L, "testBookName", "testAuthorName");
		Optional<Book> optBook = Optional.of(testBook);
		Book testBook2 = new Book(2L, "testUpdated", "testUpdated");
		Book expected = new Book(1L, "testUpdated", "testUpdated");
		Mockito.when(repo.findById(1L)).thenReturn(optBook);
		Mockito.when(repo.save(testBook)).thenReturn(testBook);
		assertThat(service.update(1L, testBook2)).usingRecursiveComparison().isEqualTo(expected);
		Mockito.verify(repo, Mockito.times(1)).findById(1L);
		Mockito.verify(repo, Mockito.times(1)).save(testBook);
	}

	@Test
	void testDelete() {
		Long bookId = 1L;
		Optional<Book> optBook = Optional.of(new Book(bookId, null, null));
		Book deleted = optBook.get();
		Mockito.when(this.repo.findById(bookId)).thenReturn(optBook);
		assertThat(this.service.delete(bookId)).isEqualTo(deleted);
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(bookId);
		Mockito.verify(this.repo, Mockito.times(1)).findById(bookId);
	}

}
