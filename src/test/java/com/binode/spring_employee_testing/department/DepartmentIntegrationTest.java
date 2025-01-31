package com.binode.spring_employee_testing.department;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepartmentIntegrationTest extends AbstractTestContainer {

    @LocalServerPort
    private int port;

    public String getAPiUrl(){
        return "http://localhost:"+port+"/api/v1/departments";
    }


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllDepartments() {
        //when

        ResponseEntity<List<Department>> response = restTemplate.exchange(
                getAPiUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Department>>() {}
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    void getDepartmentById() {
        //given
        Department department=new Department();
        department.setDepartmentName("IT");
        department.setEmployees(new ArrayList<>());

        //save department using private helper mthod
        Department saveDepartmentResponse=saveDepartment(department);

        long id = saveDepartmentResponse.getId();

        //when
        ResponseEntity<Department> response = restTemplate.exchange(
                getAPiUrl()+"/"+id,
                HttpMethod.GET,
                null,
                Department.class);


        //then

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(id);
        assertThat(response.getBody().getDepartmentName()).isEqualTo("IT");
        assertThat(response.getBody().getEmployees()).isEmpty();

    }

    @Test
    void createDepartment() {
        //given
        Department department = new Department();
        department.setDepartmentName("IT");
        department.setEmployees(new ArrayList<>());


        //when

        ResponseEntity<Department> response=restTemplate.exchange(
                getAPiUrl(),
                HttpMethod.POST,
                new HttpEntity<>(department),
                Department.class
        );


        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }



//saving the department for testing
    private Department saveDepartment(Department department) {
        ResponseEntity<Department> response = restTemplate.exchange(
                getAPiUrl(),
                HttpMethod.POST,
                new HttpEntity<>(department),
                Department.class
        );

        // Assert and return saved entity
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        return response.getBody();
    }




    private Employee saveEmployee(Employee employee, long departmentId) {
        ResponseEntity<Employee> savedEmployeeResponse = restTemplate.exchange(
                getAPiUrl()+"/employees/"+departmentId,
                HttpMethod.POST,
                new HttpEntity<>(employee),
                Employee.class
        );
        assertThat(savedEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(savedEmployeeResponse.getBody()).isNotNull();
        return savedEmployeeResponse.getBody();
    }

    @Test
    void saveEmployee() {
        //given


        Department department = new Department();
        department.setDepartmentName("IT");
        department.setEmployees(new ArrayList<>());
        Department saveDepartmentResponse=saveDepartment(department);

        long departmentId=saveDepartmentResponse.getId();


        Employee employee = new Employee();
        employee.setEmployeeName("Binod Rasaili");
        employee.setAddress("Texas");

        //when
        ResponseEntity<Employee> response = restTemplate.exchange(
                getAPiUrl()+"/employees/"+departmentId,
                HttpMethod.POST,
                new HttpEntity<>(employee),
                Employee.class
        );


        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmployeeName()).isEqualTo("Binod Rasaili");
        assertThat(response.getBody().getAddress()).isEqualTo("Texas");


    }

    @Test
    void updateEmployee() {
        //given


        Department department = new Department();
        department.setDepartmentName("IT");
        department.setEmployees(new ArrayList<>());
        //saving old department
        Department saveDepartmentResponse=saveDepartment(department);
        long oldDepartmentId=saveDepartmentResponse.getId();


        //saving another Department in which employee gets updated

        Department newDepartment = new Department();
        department.setDepartmentName("HR");
        department.setEmployees(new ArrayList<>());
        Department savedNewDepartmentResponse=saveDepartment(department);



        Employee employee = new Employee();
        employee.setEmployeeName("Binod Rasaili");
        employee.setAddress("Texas");

        Employee savedEmployeeResponse = saveEmployee(employee,oldDepartmentId);

        //update employee logic
        String updateEmployeeName="Leo Messi";
        String updateEmployeeAddress="Miami";
        long newDepartmentId=savedNewDepartmentResponse.getId();

        long savedEmployeeId= savedEmployeeResponse.getId();


        //when
        ResponseEntity<Employee>  updateEmployeeResponse = restTemplate.exchange(
                getAPiUrl()+"/employees?employeeId="+savedEmployeeId+"&employeeName="+updateEmployeeName
                +"&address="+updateEmployeeAddress+"&departmentId="+newDepartmentId,
                HttpMethod.PUT,
                null,
                Employee.class
        );


        //then
        assertThat(updateEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateEmployeeResponse.getBody()).isNotNull();
        assertThat(updateEmployeeResponse.getBody().getEmployeeName()).isEqualTo(updateEmployeeName);
        assertThat(updateEmployeeResponse.getBody().getAddress()).isEqualTo(updateEmployeeAddress);






    }

}