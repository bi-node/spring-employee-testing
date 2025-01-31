package com.binode.spring_employee_testing;

import org.springframework.boot.SpringApplication;

public class TestSpringEmployeeTestingApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringEmployeeTestingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
