package com.application.familiarbre.models.entites;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="isMaried")
public class isMarried implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User man;

    @ManyToOne
    private User women;
}
