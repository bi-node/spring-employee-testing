package com.binode.spring_employee_testing.department;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class DepartmentRepositoryTest extends AbstractTestContainer {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Test
    void findAllEmployeeByDepartmentId() {
        //given
        Department department = new Department();
        department.setDepartmentName("IT");
        department.setId(1L);
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setAddress("Texas");
        employee.setEmployeeName("Binod Rasaili");
        List<Employee> employees = List.of(employee);
        department.setEmployees(employees);
        System.out.println(department);
        departmentRepository.save(department);

        //when

        List<Employee> allEmployeeByDepartmentId = departmentRepository.findAllEmployeeByDepartmentId(1L);

        //then

        assertThat(allEmployeeByDepartmentId).isNotEmpty();
        assertThat(allEmployeeByDepartmentId).hasSize(1);
    }
}