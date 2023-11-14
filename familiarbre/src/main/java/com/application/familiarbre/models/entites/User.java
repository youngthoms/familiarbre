package com.application.familiarbre.models.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String socialSecurityNumber;
    private String email;

    @Override
    public String toString() {
        return "";
    }

    public User() {
        super();
    }

    public User(String socialSecurityNumber, String email) {
        super();
        this.socialSecurityNumber = socialSecurityNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getEmail() {
        return email;
    }
}
