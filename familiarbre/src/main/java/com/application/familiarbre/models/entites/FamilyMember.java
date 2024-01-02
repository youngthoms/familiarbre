package com.application.familiarbre.models.entites;

import jakarta.persistence.*;
import lombok.*;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "dad_id")
    private FamilyMember dad;
    @ManyToOne
    @JoinColumn(name = "mom_id")
    private FamilyMember mom;
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
