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
    private String password;

    @Override
    public String toString() {
        return "";
    }

    public User() {
        super();
    }

    public User(String socialSecurityNumber, String email, String password) {
        super();
        this.socialSecurityNumber = socialSecurityNumber;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
