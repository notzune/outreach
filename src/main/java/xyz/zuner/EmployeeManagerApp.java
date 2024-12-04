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
 * @author Zeyad
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 04 Dec 2024
 */
public class EmployeeManagerApp extends Application {

    private final StackSorter sorter = new StackSorter();
    private List<Employee> employees = new ArrayList<>();
    private TextArea displayArea;
    private final Map<Button, Boolean> toggleStates = new HashMap<>(); // tracks toggle states

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

        // layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                new Label("Sorting Options:"),
                sortByName, sortById, sortBySalary,
                new Label("File Operations:"),
                populateRandom, saveToFile, saveSortedToFile, addManual,
                new Label("Display Area:"),
                displayArea
        );

        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createToggleSortButton(String label, Function<Employee, ? extends Comparable> keyExtractor) {
        Button button = new Button(label);
        toggleStates.put(button, true); // initial is ascending

        button.setOnAction(e -> {
            boolean isAscending = toggleStates.get(button);
            Comparator<Employee> comparator = Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));

            if (!isAscending) {
                comparator = comparator.reversed();
            }

            sorter.sort(employees, comparator);
            displayArea.setText(formatEmployeeList());

            // toggle state
            toggleStates.put(button, !isAscending);
        });

        return button;
    }

    private void loadEmployees() {
        try {
            employees = new ArrayList<>(EmployeeFileHandler.loadEmployeesFromFile("Employee.txt"));
            displayArea.setText(formatEmployeeList());
        } catch (IOException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage(), Alert.AlertType.ERROR);
            employees = new ArrayList<>();
        }
    }

    private String formatEmployeeList() {
        return employees.stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));
    }

    private void populateRandomEmployees() {
        try {
            EmployeeFileHandler.populateFileWithRandomEmployees(10);
            loadEmployees(); // Reload the updated file
            showAlert("Success", "Random employees added successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to populate employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void saveEmployeesToFile() {
        try {
            EmployeeFileHandler.saveEmployeesToFile("Employee.txt", employees);
            showAlert("Success", "Employees saved to file successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to save employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void saveSortedFile() {
        try {
            EmployeeFileHandler.saveEmployeesToFile("SortedEmployee.txt", employees);
            showAlert("Success", "Sorted employees saved to SortedEmployee.txt.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to save sorted employees: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

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
                    loadEmployees(); // reload the updated file
                    showAlert("Success", "Employee added successfully.", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("Error", "Invalid input. Please ensure all fields are filled correctly.", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}