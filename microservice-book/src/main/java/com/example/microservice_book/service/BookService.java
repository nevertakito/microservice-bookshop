package com.example.microservice_book.service;

import com.example.microservice_book.model.Book;
import com.example.microservice_book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;


    public Iterable<Book> findAll(){
        return repository.findAll();
    }
    public Book findByTitleAndAuthor(String title, String author){
        return repository.findByTitleAndAuthor(title, author).get();
    }
    public Optional<Book> findById(int id){
        return repository.findById(id);
    }
    public ResponseEntity<Book> save(Book book) {
        if(repository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).isPresent())
        {
            Book book1 = repository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).get();
            book1.setQuantity(book.getQuantity()+book1.getQuantity());
            return new ResponseEntity<>(repository.save(book1), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(repository.save(book), HttpStatus.CREATED);
        }
    }
    public ResponseEntity<Book> update(int id, Book book){
        if(repository.existsById(id)){
            Book newBook = repository.findById(id).get();
            newBook.setTitle(book.getTitle());
            newBook.setAuthor(book.getAuthor());
            newBook.setDescription(book.getDescription());
            newBook.setCost(book.getCost());
            newBook.setQuantity(book.getQuantity());
            return new ResponseEntity<>(repository.save(newBook), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(repository.save(book), HttpStatus.CREATED);
        }
    }
    public void delete(int id){
        if(repository.findById(id).isPresent()) repository.deleteById(id);
    }
}
