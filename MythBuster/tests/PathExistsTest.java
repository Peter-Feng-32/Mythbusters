package tests;
import controller.Controller;
import gamefiles.rooms.BossRoom;
import gamefiles.rooms.Room;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Stack;

import static org.junit.Assert.assertTrue;

public class PathExistsTest extends ApplicationTest {
    private Controller controller;
    private int counter = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    @Test
    public void testPathExists() {
        clickOn("#HeroNameTextField");
        write("this is a valid name");
        clickOn("Begin!");
        Stack<Room> rooms = new Stack<>();
        boolean[][] visited = new boolean[6][6];
        Room curr;
        rooms.push(controller.getCurrentRoom());
        boolean passed = false; //default to fail
        while (!rooms.isEmpty()) {
            counter++;
            curr = rooms.pop();
            controller.setCurrentRoom(curr);
            if (curr instanceof BossRoom) {
                passed = true;
            }
            if (curr.getBottomDoor() != null
                    && !visited[curr.getBottomDoor().getDestination().getRow()]
                    [curr.getBottomDoor().getDestination().getColumn()]) {
                rooms.push(curr.getBottomDoor().getDestination());
                visited[curr.getBottomDoor().getDestination().getRow()]
                        [curr.getBottomDoor().getDestination().getColumn()] = true;
            }
            if (curr.getTopDoor() != null && !visited[curr.getTopDoor().getDestination().getRow()]
                    [curr.getTopDoor().getDestination().getColumn()]) {
                rooms.push(curr.getTopDoor().getDestination());
                visited[curr.getTopDoor().getDestination().getRow()]
                        [curr.getTopDoor().getDestination().getColumn()] = true;
            }
            if (curr.getRightDoor() != null
                    && !visited[curr.getRightDoor().getDestination().getRow()]
                    [curr.getRightDoor().getDestination().getColumn()]) {
                rooms.push(curr.getRightDoor().getDestination());
                visited[curr.getRightDoor().getDestination().getRow()]
                        [curr.getRightDoor().getDestination().getColumn()] = true;
            }
            if (curr.getLeftDoor() != null && !visited[curr.getLeftDoor().getDestination().getRow()]
                    [curr.getLeftDoor().getDestination().getColumn()]) {
                rooms.push(curr.getLeftDoor().getDestination());
                visited[curr.getLeftDoor().getDestination().getRow()]
                        [curr.getLeftDoor().getDestination().getColumn()] = true;
            }
            visited[curr.getRow()][curr.getColumn()] = true;
        }
        assertTrue(passed);
    }

    @Test
    public void testPathLength() {
        clickOn("#HeroNameTextField");
        write("this is a valid name");
        clickOn("Begin!");
        Stack<Room> rooms = new Stack<>();
        boolean[][] visited = new boolean[6][6];
        Room curr;
        rooms.push(controller.getCurrentRoom());
        while (!rooms.isEmpty()) {
            counter++;
            curr = rooms.pop();
            controller.setCurrentRoom(curr);
            if (curr instanceof BossRoom) {
                assertTrue(counter >= 6);
            }
            if (curr.getBottomDoor() != null 
                    && !visited[curr.getBottomDoor().getDestination().getRow()]
                    [curr.getBottomDoor().getDestination().getColumn()]) {
                rooms.push(curr.getBottomDoor().getDestination());
                visited[curr.getBottomDoor().getDestination().getRow()]
                        [curr.getBottomDoor().getDestination().getColumn()] = true;
            }
            if (curr.getTopDoor() != null && !visited[curr.getTopDoor().getDestination().getRow()]
                    [curr.getTopDoor().getDestination().getColumn()]) {
                rooms.push(curr.getTopDoor().getDestination());
                visited[curr.getTopDoor().getDestination().getRow()]
                        [curr.getTopDoor().getDestination().getColumn()] = true;
            }
            if (curr.getRightDoor() != null 
                    && !visited[curr.getRightDoor().getDestination().getRow()]
                    [curr.getRightDoor().getDestination().getColumn()]) {
                rooms.push(curr.getRightDoor().getDestination());
                visited[curr.getRightDoor().getDestination().getRow()]
                        [curr.getRightDoor().getDestination().getColumn()] = true;
            }
            if (curr.getLeftDoor() != null && !visited[curr.getLeftDoor().getDestination().getRow()]
                    [curr.getLeftDoor().getDestination().getColumn()]) {
                rooms.push(curr.getLeftDoor().getDestination());
                visited[curr.getLeftDoor().getDestination().getRow()]
                        [curr.getLeftDoor().getDestination().getColumn()] = true;
            }
            visited[curr.getRow()][curr.getColumn()] = true;
        }
    }
}
