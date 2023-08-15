package com.example.model.veri;


import lombok.*;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identity_id")
    private Identity identity;

    public User(String username, String password, Identity identity){
        this.username=username;
        this.password=password;
        this.identity=identity;
    }

}