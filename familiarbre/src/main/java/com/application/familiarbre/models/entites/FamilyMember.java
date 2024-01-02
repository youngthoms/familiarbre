package com.application.familiarbre.models.entites;

import jakarta.persistence.*;
import lombok.*;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @ManyToOne
    @JoinColumn(name = "maried_id")
    private FamilyMember maried;
    @OneToMany(mappedBy = "maried")
    List<FamilyMember> pids;
    private String socialSecurityNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "dad_id")
    private FamilyMember fid;
    @ManyToOne
    @JoinColumn(name = "mom_id")
    private FamilyMember mid;
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
