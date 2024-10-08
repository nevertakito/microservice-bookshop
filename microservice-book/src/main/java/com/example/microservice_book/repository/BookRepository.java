package com.example.microservice_book.repository;

import com.example.microservice_book.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
