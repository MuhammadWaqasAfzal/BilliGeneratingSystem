package org.visualobjectsoftware.intellijaassignment.dashboard;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.visualobjectsoftware.intellijaassignment.Main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;


public class DashboardControllerTest {

    private static Main main;

    @BeforeAll
    public static void setUpClass() {
        // Initialize JavaFX toolkit
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeAll
    public static void setup() throws Exception {
        // Initialize JavaFX for testing
        CountDownLatch latch = new CountDownLatch(1);
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> {
            main = new Main();
            try {
                main.start(new Stage()); // Start the JavaFX application with a new stage
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            return main;
        });
        latch.await(5, TimeUnit.SECONDS);

        // Navigate to the HomeController after starting the Main application
        FxRobot robot = new FxRobot();
        robot.interact(() -> {
            main.setScene("/org/visualobjectsoftware/intellijaassignment/DashboardPage.fxml", 1400, 800, 1400, 800);
        });
    }

    @Test
    public void testManageCustomerButtonClicked() {
        FxRobot robot = new FxRobot();
        robot.interact(() -> {
            // Simulate a button click
            robot.clickOn("#manageCustomerButton");
        });

        verifyThat("#errorLabel", hasText(""));

    }


    @AfterAll
    public static void tearDownClass() {
        // Shut down the JavaFX toolkit
        Platform.runLater(Platform::exit);

    }

}
