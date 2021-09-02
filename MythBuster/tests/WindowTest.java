package tests;

import controller.Controller;

import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class WindowTest extends ApplicationTest {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testWindowTitle() {
        assertEquals("MythBusters!", controller.getWindowTitle());
    }

}