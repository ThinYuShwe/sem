package com.napier.sem;

import java.util.ArrayList;

public class Department {
    private String dept_no, dept_name;
    public Employee manager;
    public ArrayList<Employee> employees = new ArrayList<>();

    public Department(String dept_no, String dept_name, Employee manager) {
        this.dept_no = dept_no;
        this.dept_name = dept_name;
        this.manager = manager;
    }

    public Employee getManager() {
        return manager;
    }

    public String getDept_no() {
        return dept_no;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
