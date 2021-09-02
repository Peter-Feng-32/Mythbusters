package tests;

import java.io.File;
import java.util.Scanner;

import controller.Controller;
import gamefiles.rooms.Room;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;
//mport static org.junit.jupiter.api.Assertions.assertTrue; //idk what this was for lol
import static org.junit.Assert.assertNotNull;

public class RoomLayoutTests extends ApplicationTest {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    @Test
    public void testRoomLayoutTextStartingAndBossRoom() {
        clickOn("#HeroNameTextField");
        write("Test");
        clickOn("Begin!");

        boolean hasStarting = false;
        boolean hasBoss = false;

        try {
            File file = new File(System.getProperty("user.dir")
                    + "/MythBuster/gamefiles/printables/RoomLayout.txt");

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'S') {
                        hasStarting = true;
                    }
                    if (line.charAt(i) == 'B') {
                        hasBoss = true;
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Exception: Reading file printables/RoomLayout.txt.");
            e.printStackTrace();
        }

        assertTrue(hasStarting);
        assertTrue(hasBoss);
    }

    @Test
    public void testStartingRoomHasFourExits() {
        clickOn("#HeroNameTextField");
        write("Test");
        clickOn("Begin!");

        Room starting = controller.getCurrentRoom();

        assertNotNull(starting.getLeftDoor());
        assertNotNull(starting.getTopDoor());
        assertNotNull(starting.getBottomDoor());
        assertNotNull(starting.getRightDoor());
    }
}
