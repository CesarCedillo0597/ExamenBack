package com.ExamenBack.workersmicroservice.service;

import com.ExamenBack.workersmicroservice.model.Employee;
import com.ExamenBack.workersmicroservice.model.EmployeeWorkedHours;
import com.ExamenBack.workersmicroservice.model.Job;
import com.ExamenBack.workersmicroservice.model.WorkedHoursRequest;
import com.ExamenBack.workersmicroservice.repository.EmployeeRepository;
import com.ExamenBack.workersmicroservice.repository.EmployeeWorkedHoursRepository;
import com.ExamenBack.workersmicroservice.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormatterPropertyEditorAdapter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeWorkedHoursService {

    @Autowired
    private EmployeeWorkedHoursRepository employeeWorkedHoursRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Transactional
    public Long addEmployeeWorkedHours(EmployeeWorkedHours employeeWorkedHours) {
        // Validar que el empleado exista

        Employee employee = employeeRepository.findById(employeeWorkedHours.getEmployee().getId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("El empleado especificado no existe.");
                });




        // Validar que el total de horas trabajadas no sea mayor a 20 horas
        if (employeeWorkedHours.getWorkedHours() > 20) {
            System.out.println("El total de horas trabajadas no puede ser mayor a 20 horas.");
            throw new IllegalArgumentException("El total de horas trabajadas no puede ser mayor a 20 horas.");
        }

        // Validar que la fecha de trabajo sea menor o igual a la actual
        LocalDate currentDate = LocalDate.now();
        if (employeeWorkedHours.getWorkedDate().isAfter(currentDate)) {
            System.out.println("La fecha de trabajo no puede ser posterior a la fecha actual.");
            throw new IllegalArgumentException("La fecha de trabajo no puede ser posterior a la fecha actual.");
        }

        // Validar que no se duplique el registro por empleado y día
        if (employeeWorkedHoursRepository.existsByEmployeeIdAndWorkedDate(employeeWorkedHours.getEmployee().getId(), employeeWorkedHours.getWorkedDate())) {
            System.out.println("El empleado ya tiene un registro de horas trabajadas para esta fecha.");
            throw new IllegalArgumentException("El empleado ya tiene un registro de horas trabajadas para esta fecha.");
        }

        // Guardar las horas trabajadas del empleado
        EmployeeWorkedHours workedHours = new EmployeeWorkedHours();
        workedHours.setEmployee(employeeWorkedHours.getEmployee());
        workedHours.setWorkedHours(employeeWorkedHours.getWorkedHours());
        workedHours.setWorkedDate(employeeWorkedHours.getWorkedDate());

        EmployeeWorkedHours savedEmployeeWorkedHours = employeeWorkedHoursRepository.save(employeeWorkedHours);
        return savedEmployeeWorkedHours.getId();
    }

    public Integer getTotalWorkedHours(WorkedHoursRequest request) {
        // Validar que el empleado exista
        Employee employee = employeeRepository.findById(request.getEmployee_id())
                .orElseThrow(() -> new IllegalArgumentException("El empleado especificado no existe."));

        // Validar que la fecha de inicio sea menor a la fecha de fin
        if (request.getStart_date().isAfter(request.getEnd_date())) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        List<EmployeeWorkedHours> employeeWorkedHoursList  = employeeWorkedHoursRepository.findByEmployeeIdAndWorkedDateBetween(request.getEmployee_id(),
                request.getStart_date(), request.getEnd_date());
        int totalHours = 0;
        
        for (EmployeeWorkedHours employeeWorkedHours : employeeWorkedHoursList) {
            totalHours += employeeWorkedHours.getWorkedHours();
        }

        return totalHours;
    }

    public Double calculateSalary(WorkedHoursRequest request){
        double totalHours = getTotalWorkedHours(request);

        Employee employee = employeeRepository.findById(request.getEmployee_id())
                .orElseThrow(() -> new IllegalArgumentException("El empleado especificado no existe."));

        Job job= employee.getJob();
        if (job == null) {
            throw new IllegalArgumentException("No se encontró el trabajo para el empleado con ID: " + request.getEmployee_id());
        }

        double hourlyRate = job.getSalary();

        return totalHours*hourlyRate;

    }
}