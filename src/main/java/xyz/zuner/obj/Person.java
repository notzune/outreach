package xyz.zuner.obj;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * Abstract class representing a generic person.
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 30 Nov 2024
 */
public abstract class Person {
    private String id;
    private String name;

    /**
     * Constructs a Person object with the given ID and name.
     *
     * @param id   the unique identifier for the person
     * @param name the name of the person
     */
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID of the person.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the person.
     *
     * @param id the new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the person.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}