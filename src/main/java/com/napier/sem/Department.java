package com.napier.sem;

public class Department {
    private String dept_no, dept_name;

    public Department(String dept_no, String dept_name) {
        this.dept_no = dept_no;
        this.dept_name = dept_name;
    }

    public String getDept_no() {
        return dept_no;
    }

    public String getDept_name() {
        return dept_name;
    }
}
