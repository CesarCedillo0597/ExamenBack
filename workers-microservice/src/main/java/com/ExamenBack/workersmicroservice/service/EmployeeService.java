package com.ExamenBack.workersmicroservice.service;


import com.ExamenBack.workersmicroservice.model.*;
import com.ExamenBack.workersmicroservice.repository.EmployeeRepository;
import com.ExamenBack.workersmicroservice.repository.GenderRepository;
import com.ExamenBack.workersmicroservice.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final GenderRepository genderRepository;
    private final JobRepository jobRepository;

    @Transactional
    public Long addEmployee(Employee employee) {
        // Validar que el nombre y apellido del empleado no existan
        if (employeeRepository.existsByNameAndLastName(employee.getName(), employee.getLastName())) {
            throw new IllegalArgumentException("El nombre y apellido del empleado ya existen.");
        }

        // Validar que el empleado sea mayor de edad
        if (employee.getBirthdate().plusYears(18).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("El empleado debe ser mayor de edad.");
        }

        // Validar que el género exista en su tabla correspondiente
        if (!genderRepository.existsById(employee.getGender().getId())) {
            throw new IllegalArgumentException("El género especificado no existe.");
        }

        // Validar que el puesto exista en su tabla correspondiente
        if (!jobRepository.existsById(employee.getJob().getId())) {
            throw new IllegalArgumentException("El puesto especificado no existe.");
        }

        // Guardar el nuevo empleado
        Employee savedEmployee = employeeRepository.save(employee);
        System.out.println("Empleado agregado correctamente. ID: " + savedEmployee.getId());
        return savedEmployee.getId();
    }

    public List<Employee> getEmployeesByJobId(Long jobId) {
        List<Employee> employees = employeeRepository.findByJobId(jobId);
        if (employees.isEmpty()) {
            System.out.println("No se encontraron empleados para el jobId especificado.");
            return null;
        }
        return employees;
    }

}
