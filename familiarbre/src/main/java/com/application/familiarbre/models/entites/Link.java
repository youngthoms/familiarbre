package com.application.familiarbre.models.entites;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "link")
public class Link implements Serializable {


    @Id
    @GeneratedValue
    int id;
    @OneToOne
    @JoinColumn(name="child_id")
    private FamilyMember child;
    @ManyToOne
    @JoinColumn(name = "dad_id")
    private FamilyMember dad;
    @ManyToOne
    @JoinColumn(name = "mom_id")
    private FamilyMember mom;


}