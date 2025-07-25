package com.katsadourose.primary_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrimaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimaryServiceApplication.class, args);
	}

}
