package com.qa.library.service;

import java.util.List;

public interface BookInterface<T> {

	T create(T createI);

	List<T> read();

	T update(Long bookId, T updateI);

	T delete(Long bookId);

	T readOne(Long bookId);
}
