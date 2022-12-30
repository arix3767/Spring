package com.kowalczyk.studentclasses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kowalczyk.studentclasses.repository")
public class StudentClassesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentClassesApplication.class, args);
	}

}
