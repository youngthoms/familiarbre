package com.application.familiarbre.models.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
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
    @ManyToMany
    private List<FamilyMember> pids;
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

    public List<FamilyMember> getPids() {
        if (this.pids==null){
            this.setPids(new ArrayList<FamilyMember>());
        }
        return pids;
    }

    public void addPid(FamilyMember pid){
        if (this.pids==null){
            this.setPids(new ArrayList<FamilyMember>());
        }
        if (!this.getPids().contains(pid)) {
            this.getPids().add(pid);
        }
    }
}
