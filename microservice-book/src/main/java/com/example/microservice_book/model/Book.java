package com.example.microservice_book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String author;
    private String description;
    private Integer cost;
    private Integer quantity;
}
