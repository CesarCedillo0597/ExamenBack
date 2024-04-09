package com.ExamenBack.workersmicroservice.controller;

import com.ExamenBack.workersmicroservice.model.Employee;
import com.ExamenBack.workersmicroservice.model.Gender;
import com.ExamenBack.workersmicroservice.model.Job;
import com.ExamenBack.workersmicroservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hello")
    public String sayHello() {
        return "Hola";
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> addEmployee(@RequestBody Map<String, Object> requestBody) {
        try {
            // Extraer los datos del requestBody
            Long genderId = ((Number) requestBody.get("gender_id")).longValue();
            Long jobId = ((Number) requestBody.get("job_id")).longValue();
            String name = (String) requestBody.get("name");
            String lastName = (String) requestBody.get("last_name");
            LocalDate birthdate = LocalDate.parse((String) requestBody.get("birthdate"));

            // Crear el objeto Employee
            Employee employee = new Employee();
            employee.setGender(new Gender(genderId));
            employee.setJob(new Job(jobId));
            employee.setName(name);
            employee.setLastName(lastName);
            employee.setBirthdate(birthdate);

            // Agregar el empleado
            System.out.println(employee.toString());
            Long insertedId = employeeService.addEmployee(employee);

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
            response.put("success", false);
            response.put("id", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/Jobs")
    public ResponseEntity<Map<String, Object>> getEmployeesByJobId(@RequestBody Map<String, Object> requestBody) {
        Long jobId = ((Number) requestBody.get("job_id")).longValue();

        try {
            List<Employee> employees = employeeService.getEmployeesByJobId(jobId);
            System.out.println(employees.toString());

            Map<String, Object> response = new HashMap<>();
            response.put("success", employees != null);
            response.put("employees", employees);


            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("employees", null);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
