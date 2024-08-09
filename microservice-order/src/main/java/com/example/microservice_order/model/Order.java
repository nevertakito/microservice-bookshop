package com.example.microservice_order.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime creationTime;
    private OrderStatus status;
    private String username;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> cart = new ArrayList<>();
    private Integer costs;
    private Integer quantitys;


    public Order(LocalDateTime creationTime, OrderStatus status, String username, List<Book> cart) {
        this.creationTime = creationTime;
        this.status = status;
        this.username = username;
        this.cart = cart;
    }
}
