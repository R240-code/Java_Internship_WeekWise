package Assignments_Week3;


//MINI PROJECT: Employee Management System

import java.io.*;
import java.util.*;

import static Assignments_Week3.EmployeeManagementSystem.*;

class Employee{
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}


public class EmployeeManagement {
    public static void main(String[] args) {
        loadFromFile();

        while (true) {

            System.out.println("\n==============================");
            System.out.println("   EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("==============================");

            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Save Data");
            System.out.println("7. Load Data");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");

            try {

                int choice =
                        Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addEmployee();
                        break;

                    case 2:
                        displayEmployees();
                        break;

                    case 3:
                        searchEmployee();
                        break;

                    case 4:
                        updateEmployee();
                        break;

                    case 5:
                        deleteEmployee();
                        break;

                    case 6:
                        saveToFile();
                        break;

                    case 7:
                        loadFromFile();
                        break;

                    case 8:
                        saveToFile();
                        System.out.println(
                                "Thank you for using Employee Management System!"
                        );
                        sc.close();
                        return;

                    default:
                        System.out.println(
                                "Invalid choice! Please choose 1-8."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Please enter a number."
                );
            }
        }

    }
}
