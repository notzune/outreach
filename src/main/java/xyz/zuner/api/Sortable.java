package xyz.zuner.api;

import java.util.Comparator;
import java.util.List;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 * <p>
 * Interface for sortable operations.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 30 Nov 2024
 */
public interface Sortable<T> {

    /**
     * Sorts a list of objects using a comparator
     *
     * @param items      the list to be sorted
     * @param comparator the comparator defining the sorting order
     */
    void sort(List<T> items, Comparator<T> comparator);

    /**
     * Searches for an item in a sorted list using binary search
     *
     * @param items the list of items
     * @param key   the item to search for
     * @param low   the low index
     * @param high  the high index
     * @return the index of the item, or -1 if not found
     */
    int binarySearch(List<T> items, T key, int low, int high);
}