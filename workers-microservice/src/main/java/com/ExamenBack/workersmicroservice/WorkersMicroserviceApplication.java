package com.ExamenBack.workersmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class WorkersMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkersMicroserviceApplication.class, args);
	}

}
