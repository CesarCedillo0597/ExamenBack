package com.ExamenBack.workersmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.ExamenBack.workersmicroservice")
public class WorkersMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkersMicroserviceApplication.class, args);
	}

}
