package com.qa.library.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.library.domain.Book;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:books-schema.sql",
		"classpath:books-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TestBookController {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper map;

	@Test
	void createTest() throws Exception {

		Book newBook = new Book("TestBookName", "TestAuthorName");

		String newBookJSON = this.map.writeValueAsString(newBook);

		RequestBuilder mockRequest = post("/createBook").contentType(MediaType.APPLICATION_JSON).content(newBookJSON);

		Book savedBook = new Book(2L, "TestBookName", "TestAuthorName");

		String savedBookJSON = this.map.writeValueAsString(savedBook);

		ResultMatcher matchStatus = status().isCreated();

		ResultMatcher matchBody = content().json(savedBookJSON);

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);

	}

	@Test
	void readTest() throws Exception {
		Book readBook = new Book(1L, "TestBookName", "TestAuthorName");
		List<Book> allBook = List.of(readBook);
		String readBookJSON = this.map.writeValueAsString(allBook);

		RequestBuilder readReq = get("/getBooks");

		ResultMatcher status = status().isOk();
		ResultMatcher body = content().json(readBookJSON);

		this.mock.perform(readReq).andExpect(status).andExpect(body);

	}

	@Test
	void updateTest() throws Exception {
		Book updateBook = new Book("TestBookName", "TestAuthorName");
		String updateBookJSON = this.map.writeValueAsString(updateBook);
		Long IDupdate = 1L;

		RequestBuilder updateReq = put("/update/" + IDupdate).contentType(MediaType.APPLICATION_JSON)
				.content(updateBookJSON);

		Book retUpdatedBook = new Book(1L, "TestBookName", "TestAuthorName");
		String retUpdatedBookJSON = this.map.writeValueAsString(retUpdatedBook);

		ResultMatcher retStatus = status().isOk();
		ResultMatcher retBody = content().json(retUpdatedBookJSON);

		this.mock.perform(updateReq).andExpect(retStatus).andExpect(retBody);
	}

	@Test
	void deleteTest() throws Exception {
		Book deleteBook = new Book(1L, "TestBookName", "TestAuthorName");
		String deleteBookJSON = this.map.writeValueAsString(deleteBook);

		Long remId = 1L;
		RequestBuilder delRequest = delete("/removeBook/" + remId);
		ResultMatcher Status = status().isOk();
		ResultMatcher Body = content().json(deleteBookJSON);

		this.mock.perform(delRequest).andExpect(Status).andExpect(Body);
	}

}
