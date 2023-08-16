package com.example.model.veri;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";


}
