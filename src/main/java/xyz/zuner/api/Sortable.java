package xyz.zuner.api;

import xyz.zuner.obj.Employee;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * Interface for sortable operations.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 30 Nov 2024
 */
public interface Sortable {
    /**
     * Sorts an array of Employees.
     *
     * @param employees the array to be sorted
     */
    void sort(Employee[] employees);

    /**
     * Searches for an employee in the array by ID.
     *
     * @param employees the array of employees
     * @param id        the ID to search for
     * @return the found Employee or null if not found
     */
    Employee search(Employee[] employees, String id);
}
