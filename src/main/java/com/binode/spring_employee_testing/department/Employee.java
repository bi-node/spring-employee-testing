package com.binode.spring_employee_testing.department;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder



public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String employeeName;
    private String address;

    public Employee() {
    }

    public Employee(long id, String employeeName, String address) {
        this.id = id;
        this.employeeName = employeeName;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
