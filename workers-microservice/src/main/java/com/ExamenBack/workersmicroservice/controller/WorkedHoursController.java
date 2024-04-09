package com.ExamenBack.workersmicroservice.controller;

import com.ExamenBack.workersmicroservice.model.Employee;
import com.ExamenBack.workersmicroservice.model.EmployeeWorkedHours;
import com.ExamenBack.workersmicroservice.model.WorkedHoursRequest;
import com.ExamenBack.workersmicroservice.service.EmployeeWorkedHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WorkedHoursController {

    private final EmployeeWorkedHoursService employeeWorkedHoursService;

    public WorkedHoursController(EmployeeWorkedHoursService workedHoursService, EmployeeWorkedHoursService employeeWorkedHoursService) {
        this.employeeWorkedHoursService = employeeWorkedHoursService;
    }

    @PostMapping("/addworked-hours")
    public ResponseEntity<Map<String, Object>>  addWorkedHours(@RequestBody Map<String, Object> requestBody) {
        try {
            // Extraer los datos del requestBody
            Long employeeId = ((Number) requestBody.get("employee_id")).longValue();
            int workedhours = ((Number) requestBody.get("worked_hours")).intValue();
            LocalDate workeddate = LocalDate.parse((String) requestBody.get("worked_date"));

            // Crear el objeto EmployeeWorkedHours
            EmployeeWorkedHours employeeWorkedHours = new EmployeeWorkedHours();
            employeeWorkedHours.setEmployee(new Employee(employeeId));
            employeeWorkedHours.setWorkedHours(workedhours);
            employeeWorkedHours.setWorkedDate(workeddate);


            // Agregar el empleado
            System.out.println(employeeWorkedHours.toString());
            Long insertedId = employeeWorkedHoursService.addEmployeeWorkedHours(employeeWorkedHours);

            // Crear el objeto de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("success", insertedId != null); // true si se insertó el registro o false en caso de error
            response.put("id", insertedId); // Id insertado o null en caso de error



            // Devolver la respuesta con el ResponseEntity
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Manejar cualquier excepción y devolver una respuesta de error
            System.out.println(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("id", null);
            response.put("success", false);


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/worked-hours")
    public ResponseEntity<Object> getTotalWorkedHours(@RequestBody WorkedHoursRequest request) {
        try {
            Integer totalWorkedHours =  employeeWorkedHoursService.getTotalWorkedHours(request);

            // Crear el objeto de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("success", totalWorkedHours != null);
            response.put("total_worked_hours", totalWorkedHours);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejar cualquier excepción y devolver una respuesta de error
            System.out.println(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("id", null);
            response.put("success", false);


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/calculate-salary")
    public ResponseEntity<?> calculateSalary(@RequestBody WorkedHoursRequest request) {
        try {
            double totalSalary = employeeWorkedHoursService.calculateSalary(request);
            return ResponseEntity.ok(totalSalary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
