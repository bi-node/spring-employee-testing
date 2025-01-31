package com.binode.spring_employee_testing.department;

import com.binode.spring_employee_testing.exceptions.DepartmentNotFoundException;
import com.binode.spring_employee_testing.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            return optionalDepartment.get();
        } else throw new DepartmentNotFoundException("Department with " + id + " not found");
    }

    public Employee addEmployee(Employee employee, long departmentId) {
        // Fetch the department
        Department department = getDepartmentById(departmentId); // Throws exception if not found

        // Add employee to the department
        if (department.getEmployees() != null) {
            department.getEmployees().add(employee);
        } else {
            department.setEmployees(List.of(employee));
        }

        // Save the employee first to ensure it gets an ID
        Employee savedEmployee = employeeRepository.save(employee);

        // Now, save the department to persist the relationship
        departmentRepository.save(department);

        return savedEmployee;  // Return the saved employee with the generated ID
    }


    public Employee updateEmployee(long employeeId, String employeeName, String address, Long newDepartmentId) {
        // Fetch the employee
        Employee updatingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with " + employeeId + " not found"));

        // Update basic employee details
        updatingEmployee.setEmployeeName(employeeName);
        updatingEmployee.setAddress(address);

        // If a new department ID is provided, update the relationship
        if (newDepartmentId != null) {
            Department newDepartment = getDepartmentById(newDepartmentId); // Throws exception if not found

            // Find and remove the employee from its current department, if any
            departmentRepository.findAll().forEach(department -> {
                if (department.getEmployees() != null && department.getEmployees().remove(updatingEmployee)) {
                    departmentRepository.save(department); // Persist the removal
                }
            });

            // Add the employee to the new department
            if (newDepartment.getEmployees() != null) {
                newDepartment.getEmployees().add(updatingEmployee);
            } else {
                newDepartment.setEmployees(List.of(updatingEmployee));
            }

            // Persist the new department relationship
            departmentRepository.save(newDepartment);
        }

        return updatingEmployee;
    }


    public void removeEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else throw new EmployeeNotFoundException("Employee with " + id + " not found");
    }


}
