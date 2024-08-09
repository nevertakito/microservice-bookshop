package com.example.microservice_user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Table(name = "orders")
public class Order {

    private int id;
    private LocalDateTime creationTime;
    private OrderStatus status;
    private String username;
    private List<Book> cart = new ArrayList<>();
    private Integer costs;
    private Integer quantitys;
}