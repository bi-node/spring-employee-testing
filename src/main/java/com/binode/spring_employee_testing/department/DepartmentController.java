package com.binode.spring_employee_testing.department;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private  final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }


    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return  new ResponseEntity(departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    @PostMapping("/employees/{departmentId}")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee, @PathVariable Long departmentId) {
        return new ResponseEntity<>(departmentService.addEmployee(employee,departmentId), HttpStatus.CREATED);
    }

    @PutMapping("/employees")
    public ResponseEntity<?> updateEmployee(@RequestParam long employeeId,
                                            @RequestParam(required = false) String employeeName,
                                            @RequestParam (required = false) String address,
                                            @RequestParam (required = false) long departmentId) {
        return new ResponseEntity<>(departmentService.updateEmployee(employeeId, employeeName, address, departmentId), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable long id) {
      departmentService.removeEmployee(id) ;
    }




}
