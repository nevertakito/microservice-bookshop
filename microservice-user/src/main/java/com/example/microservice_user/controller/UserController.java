package com.example.microservice_user.controller;

import com.example.microservice_user.model.Book;
import com.example.microservice_user.model.Order;
import com.example.microservice_user.model.User;
import com.example.microservice_user.security.SecurityConfig;
import com.example.microservice_user.service.UserService;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/books")
    public Iterable<Book> findAllBook(){
        return service.findAllBook();
    }
    @GetMapping("/orders")
    public Iterable<Order> findAllOrders(){
        return service.findAllOrders();
    }
    @GetMapping("/cart")
    public Optional<Order> findCart(){
        return service.findCart();
    }
    @GetMapping(value = "/add", params = {"title","author"})
    public Order addBook(@RequestParam String title, @RequestParam String author){
        return service.addBook(title,author);
    }
    @GetMapping("/buy")
    public Order buyOrder(){
        return service.buyOrder();
    }
    @GetMapping(value = "/cancel", params = "id")
    public Order cancelOrder(@RequestParam int id){
        return service.cancelOrder(id);
    }
    @GetMapping(value = "/del-book", params = "id")
    public Order deleteBook(@RequestParam int id){
        return service.deleteBook(id);
    }

    @PostMapping("/reg")
    public ResponseEntity<User> regUser(@RequestBody User user){
        return service.regUser(user);
    }

    @GetMapping("/all-orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<Order> findAllOrdersForAdmin(){
        return service.findAllOrdersForAdmin();
    }
    @PostMapping("/add-book")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book addBookInShop(@RequestBody Book book){
        return service.addBookInShop(book);
    }
    @GetMapping("/all-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<User> findAllUser(){
        return service.findAll();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<User> findByIdUser(@PathVariable int id){
        return service.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){
        return service.update(id,user);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
