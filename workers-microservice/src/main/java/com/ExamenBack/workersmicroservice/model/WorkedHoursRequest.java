package com.ExamenBack.workersmicroservice.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkedHoursRequest {
    private Long employee_id;
    private LocalDate start_date;
    private LocalDate end_date;
}