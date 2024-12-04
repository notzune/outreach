package xyz.zuner.api;

import xyz.zuner.obj.Employee;

import java.util.Comparator;
import java.util.List;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 * <p>
 * Handles sorting logic for a stack of employees. Allows sorting by any category dynamically, using reflection.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 01 Dec 2024
 */
public class StackSorter implements Sortable<Employee> {

    public static void bubbleSort(List<Employee> items, Comparator<Employee> comparator) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(items.get(j), items.get(j + 1)) > 0) {
                    Employee temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    }

    public static void selectionSort(List<Employee> items, Comparator<Employee> comparator) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(items.get(j), items.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            Employee temp = items.get(minIdx);
            items.set(minIdx, items.get(i));
            items.set(i, temp);
        }
    }

    public static void heapSort(List<Employee> items, Comparator<Employee> comparator) {
        int n = items.size();

        // build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(items, n, i, comparator);
        }

        for (int i = n - 1; i > 0; i--) {
            Employee temp = items.get(0);
            items.set(0, items.get(i));
            items.set(i, temp);

            heapify(items, i, 0, comparator);
        }
    }

    private static void heapify(List<Employee> items, int n, int i, Comparator<Employee> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comparator.compare(items.get(left), items.get(largest)) > 0) {
            largest = left;
        }
        if (right < n && comparator.compare(items.get(right), items.get(largest)) > 0) {
            largest = right;
        }
        if (largest != i) {
            Employee temp = items.get(i);
            items.set(i, items.get(largest));
            items.set(largest, temp);

            heapify(items, n, largest, comparator);
        }
    }

    @Override
    public void sort(List<Employee> items, Comparator<Employee> comparator) {
        items.sort(comparator);
    }

    @Override
    public int binarySearch(List<Employee> items, Employee key, int low, int high) {
        if (low > high) return -1; // item not found

        int mid = (low + high) / 2;
        int comparison = key.getId().compareTo(items.get(mid).getId());

        if (comparison == 0) return mid;
        else if (comparison < 0) return binarySearch(items, key, low, mid - 1);
        else return binarySearch(items, key, mid + 1, high);
    }
}