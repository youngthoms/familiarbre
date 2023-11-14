package com.application.familiarbre.models.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "family_members")
public class FamilyMember implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String firstName;
    private Date birthDay;
    private Date deathDay;

    public FamilyMember() { super(); }

    public FamilyMember(String firstName, String name, Date birthDay, Date deathDay) {
        super();
        this.firstName = firstName;
        this.name = name;
        this.birthDay = birthDay;
        this.deathDay = deathDay;
    }

    public Long getId() {
        return id;
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
