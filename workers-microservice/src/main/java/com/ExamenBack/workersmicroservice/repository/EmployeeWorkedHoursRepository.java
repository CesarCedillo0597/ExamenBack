package com.ExamenBack.workersmicroservice.repository;

import com.ExamenBack.workersmicroservice.model.EmployeeWorkedHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeWorkedHoursRepository extends JpaRepository<EmployeeWorkedHours, Long> {
    boolean existsByEmployeeIdAndWorkedDate(Long id, LocalDate workedDate);
    List<EmployeeWorkedHours> findByEmployeeIdAndWorkedDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}