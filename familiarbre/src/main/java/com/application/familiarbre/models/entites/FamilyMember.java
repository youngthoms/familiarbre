package com.application.familiarbre.models.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "family_members")
public class FamilyMember implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private Date birthDay;
    private Date deathDay;
    private String socialSecurityNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public Date getDeathDay() {
        if (this.deathDay != null) {
            return deathDay;
        }
        return null;
    }
}
