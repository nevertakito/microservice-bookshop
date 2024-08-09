package com.example.microservice_order.service;

import com.example.microservice_order.model.Book;
import com.example.microservice_order.model.Order;
import com.example.microservice_order.model.OrderStatus;
import com.example.microservice_order.model.User;
import com.example.microservice_order.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    public Iterable<Order> findAll(){
        return repository.findAll();
    }
    public Iterable<Order> findAllOrderByUser(String username){
        return repository.findAllByUsername(username);
    }
    public Optional<Order> findCartByUser(String username){
        return repository.findByStatusAndUsername(OrderStatus.PENDING,username);
    }

    public Order addInOrder(String username, String title, String author){
        if(repository.findByStatusAndUsername(OrderStatus.PENDING, username).isEmpty()){
            Order order = new Order(LocalDateTime.now(), OrderStatus.PENDING,username,new ArrayList<>());
            repository.save(order);
        }
        String url = String.format("http://microservice-book/book?title=%s&author=%s", title, author);
        Book book = restTemplate.getForObject(url, Book.class);
        if(book != null){
            Order order = repository.findByStatusAndUsername(OrderStatus.PENDING, username).get();
            List<Book> list = new ArrayList<>(order.getCart());
            boolean flag = false;
            int i = 0;
            for (Book b: list) {
                if(b.equals(book)){
                    flag = true;
                    break;
                }
                i++;
            }
            if(book.getQuantity()>0 && !flag){
                book.setQuantity(1);
                list.add(book);
                order.setCart(list);
                order.setQuantitys(list.stream().mapToInt(Book::getQuantity).sum());
                order.setCosts(list.stream().mapToInt(Book::getCost).sum());
                return repository.save(order);
            }
            else if(book.getQuantity()>0 && flag){
                int quantity = list.get(i).getQuantity() + 1;
                int cost = list.get(i).getCost() + book.getCost();
                list.get(i).setQuantity(quantity);
                list.get(i).setCost(cost);
                order.setCart(list);
                order.setQuantitys(list.stream().mapToInt(Book::getQuantity).sum());
                order.setCosts(list.stream().mapToInt(Book::getCost).sum());
                return repository.save(order);
            }
            else{
                return null;
            }
        }
        else {
            return null;
        }
    }

    public Order buy(String username){
        Order order = repository.findByStatusAndUsername(OrderStatus.PENDING, username).get();
        order.setStatus(OrderStatus.PROCESSING);
        return repository.save(order);
    }

    public Order cancel(String username, int id){
        Order order = repository.findByIdAndUsername(id, username).get();
        order.setStatus(OrderStatus.CANCELED);
        return repository.save(order);
    }
    public Order delBook(String username, int id){
        int i = 0;
        Order order = repository.findByStatusAndUsername(OrderStatus.PENDING, username).get();
        for (Book b: order.getCart()) {
            if(b.getId() == id){
                break;
            }
            i++;
        }
        order.getCart().remove(i);
        order.setQuantitys(order.getCart().size());
        order.setCosts(order.getCart().stream().mapToInt(Book::getCost).sum());
        return repository.save(order);
    }
}
