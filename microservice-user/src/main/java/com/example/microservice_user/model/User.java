package com.example.microservice_user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Builder
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String surname;
    private String lastname;
    @Column(unique = true)
    private String login;
    private String password;
    private String roles;
    public User(int id, String usermame){
        this.id = id;
        this.login = usermame;
    }
}
