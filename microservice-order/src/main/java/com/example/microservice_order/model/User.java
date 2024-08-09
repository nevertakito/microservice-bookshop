package com.example.microservice_order.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="users")
public class User {
    @Id
    private int id;
    private String login;
}