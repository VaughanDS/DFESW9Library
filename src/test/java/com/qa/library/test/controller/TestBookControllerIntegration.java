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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.library.domain.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:books-schema.sql",
		"classpath:books-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TestBookControllerIntegration {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {
		final Book testBook = new Book(null, "TestBookName", "TestAuthorName");
		String testBookAsJSON = this.mapper.writeValueAsString(testBook);

		final Book savedBook = new Book((long) 2, "TestBookName", "TestAuthorName");
		String savedBookAsJSON = this.mapper.writeValueAsString(savedBook);

		RequestBuilder request = post("/createBook").contentType(MediaType.APPLICATION_JSON).content(testBookAsJSON);

		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);

	}

	@Test
	void testGetAll() throws Exception {
		String savedBookAsJSON = this.mapper
				.writeValueAsString(List.of(new Book((long) 1, "TestBookName", "TestAuthorName")));

		RequestBuilder request = get("/getBooks");

		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

	@Test
	void testGetById() throws Exception {
		final Book savedBook = new Book((long) 1, "TestBookName", "TestAuthorName");
		String savedBookAsJSON = this.mapper.writeValueAsString(savedBook);

		RequestBuilder request = get("/getBook/" + savedBook.getBookId());

		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

//	@Test
//	void testGetById2() throws Exception {
//		final Book savedBook = new Book((long) 1, "TestBookName", "TestAuthorName");
//		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/createBook");
//		mockRequest.accept(MediaType.APPLICATION_JSON);
//		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
//		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(savedBook));
//		this.mvc.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
//	}

	@Test
	void testUpdate() throws Exception {
		final Book testBook = new Book((long) 1, "TestBookName", "TestAuthorName");
		final String testBookAsJSON = this.mapper.writeValueAsString(testBook);

		RequestBuilder request = put("/update/1").contentType(MediaType.APPLICATION_JSON).content(testBookAsJSON);

		ResultMatcher checkStatus = status().isAccepted();
		ResultMatcher checkContent = content().json(testBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

	@Test
	void testDelete() throws Exception {
		this.mvc.perform(delete("/removeBook/1")).andExpect(status().isNoContent());
	}

}
