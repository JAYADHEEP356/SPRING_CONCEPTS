package com.example.jwt_generation.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities", schema = "mydatabase")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @JoinColumn(name = "username", nullable = false)
    private String username;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}