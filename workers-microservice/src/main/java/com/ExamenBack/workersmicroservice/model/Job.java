package com.ExamenBack.workersmicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "JOBS")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SALARY")
    private Double salary;

    public Job(Long id) {
        this.id = id;
    }

    public Job() {
    }
}
