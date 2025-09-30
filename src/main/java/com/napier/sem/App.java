package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root",
                        "example");
//                con = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:33060/employees?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
//                        "root",
//                        "example");


                System.out.println("Successfully connected");
                Thread.sleep(10000);
                break; // exit loop
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Get an employee by ID
     */
    public Employee getEmployee(int ID) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT emp_no, first_name, last_name FROM employees WHERE emp_no = " + ID);

            if (rs.next()) {
                Employee emp = new Employee();
                emp.emp_no = rs.getInt("emp_no");
                emp.first_name = rs.getString("first_name");
                emp.last_name = rs.getString("last_name");
                return emp;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Gets all the current employees and salaries.
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";

            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> employees = new ArrayList<>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Gets all employees with their salary by a given role (title).
     * @param title The job title to filter by.
     * @return A list of employees with that role and their salary, or null if error.
     */
    public ArrayList<Employee> getSalariesByRole(String title) {
        try {
            String sql =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                            "FROM employees, salaries, titles " +
                            "WHERE employees.emp_no = salaries.emp_no " +
                            "AND employees.emp_no = titles.emp_no " +
                            "AND salaries.to_date = '9999-01-01' " +
                            "AND titles.to_date = '9999-01-01' " +
                            "AND titles.title = ? " +
                            "ORDER BY employees.emp_no ASC";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, title);   // replace ? with the given role name

            ResultSet rset = pstmt.executeQuery();
            ArrayList<Employee> employees = new ArrayList<>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details by role");
            return null;
        }
    }

    /**
     * Display a single employee
     */
    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name
                            + (emp.salary != 0 ? " | Salary: " + emp.salary : "")
                            + "\n"
                            + (emp.title != null ? emp.title + "\n" : "")
                            + (emp.dept_name != null ? emp.dept_name + "\n" : "")
                            + (emp.manager != null ? "Manager: " + emp.manager + "\n" : "")
            );
        }
    }

    /**
     * Display a list of employees and their salaries
     */
    public void displayAllSalaries(ArrayList<Employee> employees) {
        if (employees != null) {
            for (Employee emp : employees) {
                System.out.println(emp.emp_no + " "
                        + emp.first_name + " "
                        + emp.last_name + " | Salary: " + emp.salary);
            }
        } else {
            System.out.println("No employee salary data to display.");
        }
    }

    /**
     * Prints a list of employees.
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees)
    {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }
    /**
     * Main entry point
     */
    public static void main(String[] args) {
        App a = new App();

        // Connect to database
        a.connect();

        // Get a single employee
        // Extract employee salary information
        ArrayList<Employee> engineers = a.getSalariesByRole("Engineer");

        // Test the size of the returned data - should be 240124
        a.printSalaries(engineers);

        // Disconnect from database
        a.disconnect();
    }
}
