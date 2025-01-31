package com.binode.spring_employee_testing.department;

import com.binode.spring_employee_testing.exceptions.DepartmentNotFoundException;
import com.binode.spring_employee_testing.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest extends AbstractTestContainer {


    DepartmentService underTest;


    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Captor
    private ArgumentCaptor<Department> departmentArgumentCaptor;
    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;


    @BeforeEach
    void setUp() {
        underTest=new DepartmentService(departmentRepository,employeeRepository);
    }

    @Test
    void shouldGetAllDepartments() {
        //given

        //when
        underTest.getAllDepartments();

        //then
        verify(departmentRepository).findAll();
    }

    @Test
    void shouldSaveDepartment() {
        //given
        Employee employee=new Employee();
        employee.setEmployeeName("Binode");
        employee.setAddress("Texas");
        List<Employee> employees=new ArrayList<>(List.of(employee));
        Department department=new Department();
        department.setEmployees(employees);
        department.setDepartmentName("IT");


        //when
        underTest.createDepartment(department);

        //then
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department capturedDepartment=departmentArgumentCaptor.getValue();
        assertThat(capturedDepartment.getDepartmentName()).isEqualTo(department.getDepartmentName());

        assertThat(capturedDepartment.getEmployees()).hasSize(1);
        Employee capturedEmployee=capturedDepartment.getEmployees().get(0);
        assertThat(capturedEmployee.getEmployeeName()).isEqualTo(employee.getEmployeeName());
        assertThat(capturedEmployee.getAddress()).isEqualTo(employee.getAddress());



    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {

        //given
        long id=1L;
        String departmentName="IT";

        when(departmentRepository.findById(id)).thenReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(()->underTest.getDepartmentById(id))
        .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("Department with " + id + " not found");
    }

    @Test
    void shouldReturnDepartmentById(){
        // given
        long id=1L;
        String departmentName="IT";
        List<Employee> employees=new ArrayList<>();
        Department department=new Department();
        department.setDepartmentName(departmentName);
        department.setEmployees(employees);

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        //when
        underTest.getDepartmentById(id);

        //then
        verify(departmentRepository).findById(id);

    }

    @Test
    void shouldNotSaveDepartmentAndThrowExceptionIfDepartmentIsNotFound   () {
        //given
        long id=1L;
        Employee employee=new Employee();
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(()->underTest.addEmployee(employee,id))
        .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("Department with " + id + " not found");

        verify(departmentRepository,never()).save(any());

    }

    @Test
    void shouldSaveEmployeeIfDepartmentExistsAndEmployeeExists() {
        //given
        long id=1L;
        Department department=new Department();
        department.setDepartmentName("IT");
        //to trigger !null part of if employees exist
        department.setEmployees(new ArrayList<>());

        Employee employee=new Employee();
        employee.setEmployeeName("Binode");
        employee.setAddress("Texas");
        department.getEmployees().add(employee);


        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));




        //when
        underTest.addEmployee(employee,id);

        //then
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department capturedDepartment=departmentArgumentCaptor.getValue();
        assertThat(capturedDepartment.getDepartmentName()).isEqualTo(department.getDepartmentName());
        Employee captureEmployee=capturedDepartment.getEmployees().get(0);
        assertThat(captureEmployee.getEmployeeName()).isEqualTo(employee.getEmployeeName());
        assertThat(captureEmployee.getAddress()).isEqualTo(employee.getAddress());


    }

    @Test
    void shouldSaveEmployeeIfDepartmentExistsAndEmployeeDontExists() {
        //given
        long id=1L;
        Department department=new Department();
        department.setDepartmentName("IT");
        Employee employee=new Employee();
        employee.setEmployeeName("Binode");
        employee.setAddress("Texas");
        //to trigger else part of employees existence check
        department.setEmployees(null);

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));


        //when
        underTest.addEmployee(employee,id);

        //then
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department capturedDepartment=departmentArgumentCaptor.getValue();
        assertThat(capturedDepartment.getDepartmentName()).isEqualTo(department.getDepartmentName());
        Employee captureEmployee=capturedDepartment.getEmployees().get(0);
        assertThat(captureEmployee.getEmployeeName()).isEqualTo(employee.getEmployeeName());
        assertThat(captureEmployee.getAddress()).isEqualTo(employee.getAddress());


    }

    @Test

    void shouldNotUpdateEmployeeIfEmployeeNotFound() {
        //given
        long id=1L;
        String employeeName="Binode";
        String address="Texas";
        long departmentId=1L;

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(()->underTest.updateEmployee(id,employeeName,address,departmentId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee with " + id + " not found");
    }




}