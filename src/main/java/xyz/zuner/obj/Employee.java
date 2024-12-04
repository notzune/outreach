package xyz.zuner.obj;

import java.io.Serializable;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 * <p>
 * Represents an Employee, extending the Person class.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 30 Nov 2024
 */
public class Employee extends Person implements Serializable {

    private double salary;
    private String department;
    private String position;
    private int yearsOfService;

    /**
     * Constructs an Employee object.
     *
     * @param id             the employee's ID
     * @param name           the employee's name
     * @param salary         the employee's salary
     * @param department     the employee's department
     * @param position       the employee's position
     * @param yearsOfService the employee's years of service
     */
    public Employee(String id, String name, double salary, String department, String position, int yearsOfService) {
        super(id, name);
        this.salary = salary;
        this.department = department;
        this.position = position;
        this.yearsOfService = yearsOfService;
    }

    /**
     * Overloaded constructor for Employee without position and department.
     */
    public Employee(String id, String name, double salary, int yearsOfService) {
        super(id, name);
        this.salary = salary;
        this.department = "Unknown";
        this.position = "Unknown";
        this.yearsOfService = yearsOfService;
    }

    // getters and setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    @Override
    public String toString() {
        return String.format(
                "Employee{id='%s', name='%s', salary=%.2f, department='%s', position='%s', yearsOfService=%d}",
                getId(), getName(), salary, department, position, yearsOfService);
    }
}