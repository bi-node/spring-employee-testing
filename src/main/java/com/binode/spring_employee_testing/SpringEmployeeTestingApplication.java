package com.binode.spring_employee_testing;

import com.binode.spring_employee_testing.department.Department;
import com.binode.spring_employee_testing.department.DepartmentRepository;
import com.binode.spring_employee_testing.department.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringEmployeeTestingApplication  implements CommandLineRunner {

	@Autowired
	private DepartmentRepository departmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringEmployeeTestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application started");


	}
}
