package com.binode.spring_employee_testing.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    @Query("select d.employees from Department d where d.id = :departmentId")
    public List<Employee> findAllEmployeeByDepartmentId(long departmentId);
}
