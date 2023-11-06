package com.application.familiarbre.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String socialSecurityNumber;
    private String name;
    private String firstName;
    private String email;
    private Date birthDay;
    private Date deathDay;

    @Override
    public String toString() {
        return "";
    }

    public User() {
        super();
    }

    public User(String socialSecurityNumber, String name, String firstName, String email, Date birthDay, Date deathDay) {
        super();
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.birthDay = birthDay;
        this.deathDay = deathDay;
    }

    public Long getId() {
        return id;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return getFirstName() + " " + getName();
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public Date getDeathDay() {
        if (this.deathDay != null) {
            return deathDay;
        }
        return null;
    }
}
