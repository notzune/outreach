package xyz.zuner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * JavaFX application for employee management with sorting and file handling features.
 *
 * Requirements Met:
 * - Sorting algorithms (Bubble Sort, Heap Sort).
 * - Polymorphism (via {@link xyz.zuner.api.Sortable} interface).
 * - Aggregation (via {@code List<Employee>}).
 * - File I/O (via {@link EmployeeFileHandler}).
 * - Method overloading (via {@link Employee} constructors).
 * </p>
 *
 * @author Zeyad
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 04 Dec 2024
 */
public class EmployeeManagerApp extends Application {

    private final StackSorter sorter = new StackSorter();
    private List<Employee> employees = new ArrayList<>();
    private TableView<Employee> employeeTable;
    private final Map<Button, Boolean> toggleStates = new HashMap<>(); // toggle states for sort buttons
    private boolean useHeapSort = true; // tracks sorting algorithm

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Manager");

        // initialize table
        employeeTable = new TableView<>();
        initializeTable();

        // load data
        loadEmployees();

        // create sort buttons
        Button sortByName = createToggleSortButton("Sort by Name", Employee::getName);
        Button sortById = createToggleSortButton("Sort by ID", Employee::getId);
        Button sortBySalary = createToggleSortButton("Sort by Salary", Employee::getSalary);

        // create other buttons
        Button populateRandom = new Button("Populate Random Employees");
        populateRandom.setOnAction(e -> populateRandomEmployees());

        Button saveToFile = new Button("Save to File");
        saveToFile.setOnAction(e -> saveEmployeesToFile());

        Button saveSortedToFile = new Button("Save Sorted to File");
        saveSortedToFile.setOnAction(e -> saveSortedFile());

        Button addManual = new Button("Add Employee Manually");
        addManual.setOnAction(e -> modifyEmployeeFile());

        Button toggleAlgorithm = new Button("Switch to Bubble Sort");
        toggleAlgorithm.setOnAction(e -> {
            useHeapSort = !useHeapSort;
            toggleAlgorithm.setText(useHeapSort ? "Switch to Bubble Sort" : "Switch to Heap Sort");
            showAlert("Info", "Sorting algorithm switched to " + (useHeapSort ? "Heap Sort" : "Bubble Sort"),
                    Alert.AlertType.INFORMATION);
        });

        Button sortButton = new Button("Sort and Measure Time");
        sortButton.setOnAction(e -> sortAndMeasurePerformance());

        // set layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                new Label("Sorting Options:"),
                toggleAlgorithm, sortButton, sortByName, sortById, sortBySalary,
                new Label("File Operations:"),
                populateRandom, saveToFile, saveSortedToFile, addManual,
                new Label("Employee Table:"),
                employeeTable
        );

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the TableView with columns for employee attributes.
     */
    private void initializeTable() {
        TableColumn<Employee, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Employee, Integer> yearsColumn = new TableColumn<>("Years of Service");
        yearsColumn.setCellValueFactory(new PropertyValueFactory<>("yearsOfService"));

        employeeTable.getColumns().addAll(idColumn, nameColumn, salaryColumn, departmentColumn, positionColumn, yearsColumn);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // auto-resize columns
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
        toggleStates.put(button, true); // default to ascending

        button.setOnAction(e -> {
            boolean isAscending = toggleStates.get(button);
            Comparator<Employee> comparator = Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));

            if (!isAscending) comparator = comparator.reversed();

            long startTime = System.nanoTime();
            sorter.sort(employees, comparator);
            long endTime = System.nanoTime();

            updateTable();
            showAlert("Performance", label + " completed in " + (endTime - startTime) + " nanoseconds.",
                    Alert.AlertType.INFORMATION);

            toggleStates.put(button, !isAscending); // toggle state
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
        updateTable();
        showAlert("Performance", "Sorting completed in " + (endTime - startTime) + " nanoseconds.",
                Alert.AlertType.INFORMATION);
    }

    /**
     * Updates the employee table with the current list of employees.
     */
    private void updateTable() {
        employeeTable.getItems().setAll(employees);
    }

    /**
     * Loads employees from a file and displays them in the display area.
     *
     * @see EmployeeFileHandler#loadEmployeesFromFile(String)
     */
    private void loadEmployees() {
        try {
            employees = new ArrayList<>(EmployeeFileHandler.loadEmployeesFromFile("Employee.txt"));
            updateTable();
        } catch (IOException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage(), Alert.AlertType.ERROR);
            employees = new ArrayList<>();
        }
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