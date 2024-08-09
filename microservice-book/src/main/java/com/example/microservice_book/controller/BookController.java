package com.example.microservice_book.controller;

import com.example.microservice_book.model.Book;
import com.example.microservice_book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<Book> findAllBook(){
        return service.findAll();
    }
    @GetMapping(params = {"title","author"})
    public Book findByTitleBook(@RequestParam String title, @RequestParam String author){
        return service.findByTitleAndAuthor(title, author);
    }
    @GetMapping(params = "id")
    public Optional<Book> findByIdBook(@RequestParam int id){
        return service.findById(id);
    }
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book){
        return service.save(book);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        return service.update(id,book);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}