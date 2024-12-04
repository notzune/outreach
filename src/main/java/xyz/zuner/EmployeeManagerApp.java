package xyz.zuner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.zuner.api.StackSorter;
import xyz.zuner.handlers.EmployeeFileHandler;
import xyz.zuner.obj.Employee;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>21:198:335:02 Data Structures & Algorithms</p>
 * <p>Data Structures Final Project</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 * <p>
 * JavaFX Application for Employee Management with sorting options.
 *
 * Requirements Met:
 * - Sorting Algorithms (Bubble Sort, Heap Sort).
 * - Polymorphism (via {@link xyz.zuner.api.Sortable} interface).
 * - Aggregation (via {@code List<Employee>}).
 * - File I/O (via {@link EmployeeFileHandler}).
 * - Method Overloading (via {@link Employee} constructors).
 * </p>
 *
 * @author Zeyad
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 04 Dec 2024
 */
public class EmployeeManagerApp extends Application {

    private final StackSorter sorter = new StackSorter();
    private List<Employee> employees = new ArrayList<>();
    private TextArea displayArea;
    private final Map<Button, Boolean> toggleStates = new HashMap<>(); // tracks toggle states between ascend./descend.
    private boolean useHeapSort = true; // tracks sorting algorithm

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Manager");

        // init display area
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(200);

        // load employees from file
        loadEmployees();

        // create sorting buttons:
        Button sortByName = createToggleSortButton("Sort by Name", Employee::getName);
        Button sortById = createToggleSortButton("Sort by ID", Employee::getId);
        Button sortBySalary = createToggleSortButton("Sort by Salary", Employee::getSalary);

        // other buttons:
        Button populateRandom = new Button("Populate Random Employees");
        populateRandom.setOnAction(e -> populateRandomEmployees());

        Button saveToFile = new Button("Save to File");
        saveToFile.setOnAction(e -> saveEmployeesToFile());

        Button saveSortedToFile = new Button("Save Sorted to File");
        saveSortedToFile.setOnAction(e -> saveSortedFile());

        Button addManual = new Button("Add Employee Manually");
        addManual.setOnAction(e -> modifyEmployeeFile());

        // Sorting algorithm toggle button
        Button toggleAlgorithm = new Button("Switch to Bubble Sort");
        toggleAlgorithm.setOnAction(e -> {
            useHeapSort = !useHeapSort;
            toggleAlgorithm.setText(useHeapSort ? "Switch to Bubble Sort" : "Switch to Heap Sort");
            showAlert("Info", "Sorting algorithm switched to " + (useHeapSort ? "Heap Sort" : "Bubble Sort"),
                    Alert.AlertType.INFORMATION);
        });

        // sorting button with performance readout
        Button sortButton = new Button("Sort and Measure Time");
        sortButton.setOnAction(e -> sortAndMeasurePerformance());

        // layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                new Label("Sorting Options:"),
                toggleAlgorithm, sortButton, sortByName, sortById, sortBySalary,
                new Label("File Operations:"),
                populateRandom, saveToFile, saveSortedToFile, addManual,
                new Label("Display Area:"),
                displayArea
        );

        Scene scene = new Scene(layout, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a toggleable sort button that alternates between ascending and descending.
     *
     * @param label        the button label
     * @param keyExtractor the key extractor function for sorting
     * @return the created button
     * @see StackSorter#sort(List, Comparator)
     */
    private Button createToggleSortButton(String label, Function<Employee, ? extends Comparable> keyExtractor) {
        Button button = new Button(label);
        toggleStates.put(button, true); // initially ascending order

        button.setOnAction(e -> {
            boolean isAscending = toggleStates.get(button);
            Comparator<Employee> comparator = Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));

            if (!isAscending) {
                comparator = comparator.reversed();
            }

            long startTime = System.nanoTime();
            sorter.sort(employees, comparator);
            long endTime = System.nanoTime();

            displayArea.setText(formatEmployeeList());
            showAlert("Performance", label + " completed in " + (endTime - startTime) + " nanoseconds.",
                    Alert.AlertType.INFORMATION);

            // Toggle state
            toggleStates.put(button, !isAscending);
        });

        return button;
    }

    /**
     * Measures and displays the performance of the selected sorting algorithm.
     *
     * @see StackSorter#heapSort(List, Comparator)
     * @see StackSorter#bubbleSort(List, Comparator)
     */
    private void sortAndMeasurePerformance() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        long startTime = System.nanoTime();

        if (useHeapSort) {
            StackSorter.heapSort(employees, comparator);
        } else {
            StackSorter.bubbleSort(employees, comparator);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        displayArea.setText(formatEmployeeList());
        showAlert("Performance", "Sorting completed in " + duration + " nanoseconds.",
                Alert.AlertType.INFORMATION);
    }

    /**
     * Loads employees from a file and displays them in the display area.
     *
     * @see EmployeeFileHandler#loadEmployeesFromFile(String)
     */
    private void loadEmployees() {
        try {
            employees = new ArrayList<>(EmployeeFileHandler.loadEmployeesFromFile("Employee.txt"));
            displayArea.setText(formatEmployeeList());
        } catch (IOException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage(), Alert.AlertType.ERROR);
            employees = new ArrayList<>();
        }
    }

    /**
     * Formats the list of employees into a string for display.
     *
     * @return the formatted employee list as a string
     */
    private String formatEmployeeList() {
        return employees.stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Populates the employee file with random employees and reloads the list.
     *
     * @see EmployeeFileHandler#populateFileWithRandomEmployees(int)
     */
    private void populateRandomEmployees() {
        try {
            EmployeeFileHandler.populateFileWithRandomEmployees(30);
            loadEmployees(); // Reload the updated file
            showAlert("Success", "Random employees added successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to populate employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Saves the current employee list to a file.
     *
     * @see EmployeeFileHandler#saveEmployeesToFile(String, List)
     */
    private void saveEmployeesToFile() {
        try {
            EmployeeFileHandler.saveEmployeesToFile("Employee.txt", employees);
            showAlert("Success", "Employees saved to file successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to save employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Saves the sorted employee list to a file.
     *
     * @see EmployeeFileHandler#saveEmployeesToFile(String, List)
     */
    private void saveSortedFile() {
        try {
            EmployeeFileHandler.saveEmployeesToFile("SortedEmployee.txt", employees);
            showAlert("Success", "Sorted employees saved to SortedEmployee.txt.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to save sorted employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Opens a dialog for manually adding an employee and updates the list.
     *
     * @see EmployeeFileHandler#addEmployeeManually(String, String, double, String, String, int)
     */
    private void modifyEmployeeFile() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter the details of the new employee:");

        TextField idField = new TextField();
        idField.setPromptText("Employee ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Employee Name");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");

        TextField positionField = new TextField();
        positionField.setPromptText("Position");

        TextField yearsOfServiceField = new TextField();
        yearsOfServiceField.setPromptText("Years of Service");

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.getChildren().addAll(
                new Label("Employee ID:"), idField,
                new Label("Name:"), nameField,
                new Label("Salary:"), salaryField,
                new Label("Department:"), departmentField,
                new Label("Position:"), positionField,
                new Label("Years of Service:"), yearsOfServiceField
        );

        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    String department = departmentField.getText();
                    String position = positionField.getText();
                    int yearsOfService = Integer.parseInt(yearsOfServiceField.getText());

                    EmployeeFileHandler.addEmployeeManually(id, name, salary, department, position, yearsOfService);
                    loadEmployees(); // Reload the updated file
                    showAlert("Success", "Employee added successfully.", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("Error", "Invalid input. Please ensure all fields are filled correctly.", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    /**
     * Displays an alert dialog.
     *
     * @param title   the title of the alert
     * @param content the content of the alert
     * @param type    the type of the alert
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}