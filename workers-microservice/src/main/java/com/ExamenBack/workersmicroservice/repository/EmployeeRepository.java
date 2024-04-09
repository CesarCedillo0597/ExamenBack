package com.ExamenBack.workersmicroservice.repository;

import com.ExamenBack.workersmicroservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByNameAndLastName(String name, String lastName);

    List<Employee> findByJobId(Long id);



}