package com.qa.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.library.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}