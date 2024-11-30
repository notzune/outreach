package xyz.zuner;

import xyz.zuner.api.Sortable;
import xyz.zuner.obj.Employee;

import java.io.*;
import java.util.Scanner;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * Main application logic. Manages employee operations such as sorting and searching.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 16 Nov 2024
 * @updated 30 Nov 2024
 */
public class EmployeeManager implements Sortable {
    private static int employeeCount = 0; // counter for total employees processed

    public static void main(String[] args) {
        try {
            Employee[] employees = readEmployeesFromFile("Employee.txt");
            EmployeeManager manager = new EmployeeManager();

            // sort employees and save to file
            manager.sort(employees);
            saveEmployeesToFile("SortedEmployee.txt", employees);

            // search for an employee
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter an employee ID to search: ");
            String searchId = scanner.nextLine();
            Employee found = manager.search(employees, searchId);
            if (found != null) {
                System.out.println("Employee found: " + found);
            } else {
                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Reads employee data from a file.
     *
     * @param filename the file to read from
     * @return an array of Employees
     * @throws IOException if there is an error reading the file
     */
    private static Employee[] readEmployeesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Employee[] employees = new Employee[5];
        int index = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String id = line;
            String name = reader.readLine();
            double salary = Double.parseDouble(reader.readLine());
            String department = reader.readLine();
            String position = reader.readLine();
            int yearsOfService = Integer.parseInt(reader.readLine());
            employees[index++] = new Employee(id, name, salary, department, position, yearsOfService);
            employeeCount++;
        }
        reader.close();
        return employees;
    }

    /**
     * Saves employee data to a file.
     *
     * @param filename  the file to save to
     * @param employees the array of Employees
     * @throws IOException if there is an error writing to the file
     */
    private static void saveEmployeesToFile(String filename, Employee[] employees) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Employee e : employees) {
            writer.write(e.toString());
            writer.newLine();
        }
        writer.close();
    }

    @Override
    public void sort(Employee[] employees) {
        heapSort(employees);
    }

    /**
     * Heap sort implementation for employees.
     *
     * @param employees the array to sort
     */
    private void heapSort(Employee[] employees) {
        int n = employees.length;

        // build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(employees, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Employee temp = employees[0];
            employees[0] = employees[i];
            employees[i] = temp;
            heapify(employees, i, 0);
        }
    }

    private void heapify(Employee[] employees, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && employees[left].getSalary() > employees[largest].getSalary()) {
            largest = left;
        }

        if (right < n && employees[right].getSalary() > employees[largest].getSalary()) {
            largest = right;
        }

        if (largest != i) {
            Employee swap = employees[i];
            employees[i] = employees[largest];
            employees[largest] = swap;

            heapify(employees, n, largest);
        }
    }

    @Override
    public Employee search(Employee[] employees, String id) {
        return binarySearch(employees, id, 0, employees.length - 1);
    }

    private Employee binarySearch(Employee[] employees, String id, int low, int high) {
        if (low > high) return null;

        int mid = (low + high) / 2;
        int comparison = id.compareTo(employees[mid].getId());
        if (comparison == 0) return employees[mid];
        else if (comparison < 0) return binarySearch(employees, id, low, mid - 1);
        else return binarySearch(employees, id, mid + 1, high);
    }
}