package org.visualobjectsoftware.intellijaassignment;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private static Main main;

    @BeforeAll
    public static void setup() throws Exception {
        // Initialize JavaFX for testing
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            main = new Main();
            try {
                main.start(new Stage()); // Start the JavaFX application with a new stage
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);

        // Initialize TestFX
        FxToolkit.registerPrimaryStage();
    }

    @Test
    public void testSetScene() {
        // Perform your test
        FxRobot robot = new FxRobot();
        robot.interact(() -> {
            main.setScene("DashboardPage.fxml", 600, 600, 600, 600);
        });

        // Verify the expected behavior, e.g., check the title of the primaryStage
        assertEquals("Bill Management System", main.primaryStage.getTitle());
    }
}
