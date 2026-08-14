package Assignments_Week3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;

public class EmployeeManagementSystem{
    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "employees.txt";


    public static void addEmployee() {
        try {
            System.out.print("Enter employee ID: ");
            int id = Integer.parseInt(sc.nextLine());

            for (Employee emp : employees) {
                if (emp.getId() == id) {
                    System.out.println("Employee with this ID already exists.");
                    return;
                }
            }

            System.out.println("Enter employee name: ");
            String name = sc.nextLine();

           if (name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty.");

            }

            System.out.print("Enter employee department: ");
            String department = sc.nextLine();
            if (department.isEmpty()) {
                throw new IllegalArgumentException("Department cannot be empty.");
            }

            System.out.print("Enter employee salary: ");
            double salary = Double.parseDouble(sc.nextLine());
            if (salary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative.");
            }

            Employee employee = new Employee(id, name, department, salary);
            employees.add(employee);
            System.out.println("Employee added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }
        System.out.println("\n__________Employee List:___________");

        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public static void searchEmployee(){
        try{

            System.out.print("Enter Employee ID to search: ");
            int id = Integer.parseInt(sc.nextLine());

            for (Employee emp : employees) {

                if (emp.getId() == id) {

                    System.out.println("Employee Found!");
                    System.out.println(emp);
                    return;
                }
            }

            System.out.println("Employee not found.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid ID! Please enter a number.");
        }
    }

    public static void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            for (Employee emp : employees) {
                if (emp.getId() == id) {

                    System.out.print("Enter new name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter new department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter new salary: ");
                    double salary = Double.parseDouble(sc.nextLine());

                    if (salary < 0) {
                        throw new IllegalArgumentException(
                                "Salary cannot be negative."
                        );
                    }

                    emp.setName(name);
                    emp.setDepartment(department);
                    emp.setSalary(salary);

                    System.out.println("Employee updated successfully!");
                    return;
                }
            }

            System.out.println("Employee not found.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid input!");

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteEmployee() {

        try {

            System.out.print("Enter Employee ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            Iterator<Employee> iterator = employees.iterator();

            while (iterator.hasNext()) {

                Employee emp = iterator.next();

                if (emp.getId() == id) {

                    iterator.remove();

                    System.out.println(
                            "Employee deleted successfully!"
                    );

                    return;
                }
            }

            System.out.println("Employee not found.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid ID!");
        }
    }


    public static void saveToFile() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Employee emp : employees) {

                writer.write(
                        emp.getId() + "," +
                                emp.getName() + "," +
                                emp.getDepartment() + "," +
                                emp.getSalary()
                );

                writer.newLine();
            }

            System.out.println("Employee data saved successfully!");

        } catch (IOException e) {

            System.out.println(
                    "Error while saving file: " + e.getMessage()
            );
        }
    }

    public static void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            System.out.println(
                    "No existing employee file found."
            );

            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            employees.clear();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String department = data[2];
                    double salary = Double.parseDouble(data[3]);

                    Employee emp =
                            new Employee(
                                    id,
                                    name,
                                    department,
                                    salary
                            );

                    employees.add(emp);
                }
            }

            System.out.println(
                    "Employee data loaded successfully!"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error while loading file: " + e.getMessage()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid data found in file."
            );
        }
    }








}
