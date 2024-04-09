package com.ExamenBack.workersmicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GENDER_ID", referencedColumnName = "id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "JOB_ID", referencedColumnName = "id")
    private Job job;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    public Employee(Long id) {
        this.id = id;
    }

    public Employee() {
    }
}