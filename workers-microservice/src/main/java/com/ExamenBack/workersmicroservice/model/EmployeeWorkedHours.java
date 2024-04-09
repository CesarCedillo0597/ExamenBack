package com.ExamenBack.workersmicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "EMPLOYEE_WORKED_HOURS")
public class EmployeeWorkedHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "WORKED_HOURS")
    private int workedHours;

    @Column(name = "WORKED_DATE")
    private LocalDate workedDate;

}
