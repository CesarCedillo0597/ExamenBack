package com.ExamenBack.workersmicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "GENDERS")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Gender(Long id) {
        this.id = id;
    }

    public Gender() {
    }
}
