package xyz.zuner.handlers;

import xyz.zuner.obj.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 * <p>
 * Handles file operations for employee data, including populating
 * the Employee.txt file with randomly generated employees and
 * allowing manual input of employee data.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 30 Nov 2024
 */
public class EmployeeFileHandler {

    private static final String MAIN_FILE_NAME = "Employee.txt";
    private static final Random RANDOM = new Random();

    private static final String[] DEPARTMENTS = {"Accounting", "IT", "HR", "Marketing", "Operations"};
    private static final String[] POSITIONS = {"Manager", "Technician", "Analyst", "Clerk", "Supervisor"};

    /**
     * Populates the "Employee.txt" file with randomly generated employees
     *
     * @param count the number of random employees to generate
     * @throws IOException if an I/O error occurs
     */
    public static void populateFileWithRandomEmployees(int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MAIN_FILE_NAME))) {
            for (int i = 0; i < count; i++) {
                Employee employee = generateRandomEmployee();
                writeEmployeeToFile(writer, employee);
            }
        }
        System.out.println("Generated " + count + " random employees and saved to " + MAIN_FILE_NAME);
    }

    /**
     * Loads employees from a file
     *
     * @param filename the file to load employees from
     * @return a stack of employees
     * @throws IOException if an I/O error occurs
     */
    public static List<Employee> loadEmployeesFromFile(String filename) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String id = line;
            String name = reader.readLine();
            double salary = Double.parseDouble(reader.readLine());
            String department = reader.readLine();
            String position = reader.readLine();
            int yearsOfService = Integer.parseInt(reader.readLine());
            employees.add(new Employee(id, name, salary, department, position, yearsOfService));
        }
        reader.close();
        return employees;
    }

    /**
     * Saves employees to a file
     *
     * @param filename  the name of the file
     * @param employees the list of employees to save
     * @throws IOException if an I/O error occurs
     */
    public static void saveEmployeesToFile(String filename, List<Employee> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Employee employee : employees) {
                writeEmployeeToFile(writer, employee);
            }
        }
    }

    /**
     * Adds a new employee and appends it to the main file.
     *
     * @param id             the employee's ID
     * @param name           the employee's name
     * @param salary         the employee's salary
     * @param department     the employee's department
     * @param position       the employee's position
     * @param yearsOfService the employee's years of service
     * @throws IOException if an I/O error occurs
     */
    public static void addEmployeeManually(String id, String name, double salary, String department, String position, int yearsOfService) throws IOException {
        Employee employee = new Employee(id, name, salary, department, position, yearsOfService);

        // append the employee to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MAIN_FILE_NAME, true))) {
            writeEmployeeToFile(writer, employee);
        }

        System.out.println("Employee added successfully!");
    }

    private static Employee generateRandomEmployee() {
        String id = String.format("%06d", RANDOM.nextInt(999999));
        String name = generateRandomName();
        double salary = Math.round((50000 + (RANDOM.nextDouble() * 50000)) * 100.0) / 100.0;
        String department = DEPARTMENTS[RANDOM.nextInt(DEPARTMENTS.length)];
        String position = POSITIONS[RANDOM.nextInt(POSITIONS.length)];
        int yearsOfService = RANDOM.nextInt(20);
        return new Employee(id, name, salary, department, position, yearsOfService);
    }

    private static String generateRandomName() {
        String[] firstNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia"};
        return firstNames[RANDOM.nextInt(firstNames.length)] + " " + lastNames[RANDOM.nextInt(lastNames.length)];
    }

    private static void writeEmployeeToFile(BufferedWriter writer, Employee employee) throws IOException {
        writer.write(employee.getId());
        writer.newLine();
        writer.write(employee.getName());
        writer.newLine();
        writer.write(String.valueOf(employee.getSalary()));
        writer.newLine();
        writer.write(employee.getDepartment());
        writer.newLine();
        writer.write(employee.getPosition());
        writer.newLine();
        writer.write(String.valueOf(employee.getYearsOfService()));
        writer.newLine();
    }
}