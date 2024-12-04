//package xyz.zuner;
//
//import javafx.embed.swing.SwingFXUtils;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.image.WritableImage;
//import javafx.scene.robot.Robot;
//import javafx.stage.Stage;
//import org.junit.Test;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.util.WaitForAsyncUtils;
//
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * <p>21:198:335:02 Data Structures & Algorithms</p>
// * <p>Data Structures Final Project</p>
// * <p>Rutgers ID: 199009651</p>
// * <br>
// * <p>
// * Uses TestFX to test the JavaFX application. This class is for testing purposes only and is
// * intended to collect screenshots for documentation. It is not meant for production use.
// * </p>
// *
// * @author Zeyad
// * @mailto zmr15@scarletmail.rutgers.edu
// * @created 04 Dec 2024
// */
//public class EmployeeManagerAppTest extends ApplicationTest {
//
//    private FxRobot fxRobot;
//    private Robot robot;
//
//    /**
//     * Launches the JavaFX application.
//     *
//     * @param stage the primary stage for the application
//     */
//    @Override
//    public void start(Stage stage) {
//        new EmployeeManagerApp().start(stage); // launch app
//        robot = new Robot(); // initialize javafx robot for screenshots
//    }
//
//    /**
//     * Captures screenshots of all screens in the Employee Manager application.
//     */
//    @Test
//    public void captureAllScreenshots() {
//        fxRobot = new FxRobot();
//
//        // list of button labels to click
//        List<String> buttons = List.of(
//                "Sort by Name",
//                "Sort by ID",
//                "Sort by Salary",
//                "Populate Random Employees",
//                "Save to File",
//                "Save Sorted to File",
//                "Add Employee Manually",
//                "Switch to Bubble Sort",
//                "Sort and Measure Time"
//        );
//
//        // iterate through buttons, simulate clicks, and capture screenshots
//        for (String buttonLabel : buttons) {
//            try {
//                clickButton(buttonLabel); // click the button
//                captureScreenshot(buttonLabel + ".png"); // save the screenshot
//                waitForUIUpdate(); // ensure ui is stable
//            } catch (Exception e) {
//                System.err.println("failed to capture screenshot for button: " + buttonLabel);
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Simulates a button click based on the button label or ID.
//     *
//     * @param buttonLabel the label or ID of the button
//     */
//    private void clickButton(String buttonLabel) {
//        fxRobot.clickOn(buttonLabel); // simulate button click
//    }
//
//    /**
//     * Captures the current state of the stage and saves it as a screenshot.
//     *
//     * @param fileName the name of the screenshot file to save
//     */
//    private void captureScreenshot(String fileName) {
//        try {
//            // Define the region to capture. Adjust coordinates and dimensions as necessary.
//            Rectangle2D screenBounds = new Rectangle2D(0, 0, 800, 600); // example for 800x600 stage size
//
//            WritableImage screenshot = robot.getScreenCapture(null, screenBounds); // capture specified region
//            File outputFile = new File("screenshots/" + fileName); // create output file
//            outputFile.getParentFile().mkdirs(); // ensure directory exists
//            ImageIO.write(
//                    SwingFXUtils.fromFXImage(screenshot, null), // convert WritableImage
//                    "png",
//                    outputFile
//            ); // save screenshot
//            System.out.println("screenshot saved: " + outputFile.getAbsolutePath());
//        } catch (IOException e) {
//            System.err.println("failed to save screenshot: " + fileName);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Waits for the JavaFX UI to stabilize before taking the next action.
//     */
//    private void waitForUIUpdate() {
//        WaitForAsyncUtils.waitForFxEvents(); // wait for ui events to finish
//    }
//}