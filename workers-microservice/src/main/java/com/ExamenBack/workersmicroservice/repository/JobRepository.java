package com.ExamenBack.workersmicroservice.repository;

import com.ExamenBack.workersmicroservice.model.Employee;
import com.ExamenBack.workersmicroservice.model.EmployeeWorkedHours;
import com.ExamenBack.workersmicroservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}