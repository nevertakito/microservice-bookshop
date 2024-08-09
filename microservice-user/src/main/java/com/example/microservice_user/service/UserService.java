package com.example.microservice_user.service;

import com.example.microservice_user.model.Book;
import com.example.microservice_user.model.Order;
import com.example.microservice_user.model.User;
import com.example.microservice_user.repository.UserRepository;
import com.example.microservice_user.security.SecurityConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    SecurityConfig security;
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${book.url}")
    String bookUrl;
    @Value("${order.url}")
    String orderUrl;
    public Iterable<Book> findAllBook(){
        String url = String.format("http://%s/book/all", bookUrl);
        return restTemplate.getForObject(url, Iterable.class);
    }
    public Iterable<Order> findAllOrders(){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/all?username=%s", orderUrl, username);
        return restTemplate.getForObject(url, Iterable.class);
    }
    public Optional<Order> findCart(){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/cart?username=%s", orderUrl, username);
        return restTemplate.getForObject(url, Optional.class);
    }

    public Order addBook(String title, String author){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/add?username=%s&title=%s&author=%s", orderUrl,username,title,author);
        return restTemplate.getForObject(url, Order.class);
    }
    public Order buyOrder(){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/buy?username=%s", orderUrl, username);
        return restTemplate.getForObject(url, Order.class);
    }
    public Order cancelOrder(int id){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/cancel?username=%s&id=%s", orderUrl, username,id);
        return restTemplate.getForObject(url, Order.class);

    }
    public Order deleteBook(int id){
        String username = security.getCurrentUsername();
        String url = String.format("http://%s/order/del-book?username=%s&id=%s", orderUrl, username,id);
        return restTemplate.getForObject(url, Order.class);
    }
    public ResponseEntity<User> regUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.findByLogin(user.getLogin()).isPresent()
                ? new ResponseEntity(repository.findByLogin(user.getLogin()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
    }

    public Iterable<Order> findAllOrdersForAdmin(){
        String url = String.format("http://%s/order/all-admin", orderUrl);
        return restTemplate.getForObject(url, Iterable.class);
    }
    public Book addBookInShop(Book book){
        String url = String.format("http://%s/book", bookUrl);
        return restTemplate.postForObject(url,book, Book.class);
    }

    public Iterable<User> findAll(){
        return repository.findAll();
    }
    public Optional<User> findById(int id){
        return repository.findById(id);
    }
    public ResponseEntity<User> update(int id, User user){
        if(repository.existsById(id)){
            User newUser = repository.findById(id).get();
            newUser.setFirstname(user.getFirstname());
            newUser.setSurname(user.getSurname());
            newUser.setLastname(user.getLastname());
            newUser.setLogin(user.getLogin());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setRoles(user.getRoles());
            return new ResponseEntity<>(repository.save(newUser), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
        }
    }
    public void delete(int id){
        if(repository.findById(id).isPresent()) repository.deleteById(id);
    }
}
