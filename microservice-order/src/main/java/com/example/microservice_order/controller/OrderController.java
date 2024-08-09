package com.example.microservice_order.controller;

import com.example.microservice_order.model.Book;
import com.example.microservice_order.model.Order;
import com.example.microservice_order.model.User;
import com.example.microservice_order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/all-admin")
    public Iterable<Order> findAllForAdmin(){
        return service.findAll();
    }
    @GetMapping(value = "/all", params = "username")
    public Iterable<Order> findAll(@RequestParam String username){
        return service.findAllOrderByUser(username);
    }
    @GetMapping(value = "/cart", params = "username")
    public Optional<Order> findCart(@RequestParam String username){
        return service.findCartByUser(username);
    }
    @GetMapping(value = "/add", params = {"username","title","author"})
    public Order addInOrder(@RequestParam String username,@RequestParam String title, @RequestParam String author){
        return service.addInOrder(username, title, author);
    }
    @GetMapping(value = "/buy", params = "username")
    public Order buyOrder(@RequestParam String username){
        return service.buy(username);
    }
    @GetMapping(value = "/cancel", params = {"username","id"})
    public Order cancelOrder(@RequestParam String username, @RequestParam int id){
        return service.cancel(username, id);
    }
    @GetMapping(value = "/del-book", params = {"username","id"})
    public Order delBookInCart(@RequestParam String username, @RequestParam int id){
        return service.delBook(username, id);
    }
}
