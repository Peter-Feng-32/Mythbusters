package tests.M5;

import controller.Controller;
import gamefiles.RedTreasureChest;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TreasureRoomAndTrollDeathTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testTreasureRoomExists() throws IOException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("Begin!");
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")
            + "/MythBuster/gamefiles/printables/RoomLayout.txt"));
        StringBuilder sb = new StringBuilder();

        String line = br.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
        }
        String map = sb.toString();
        br.close();
        assertTrue("message", map.contains("T"));
    }
    @Test
    public void testPreventTrollDeath() throws InterruptedException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("Begin!");
        RedTreasureChest redChest = new RedTreasureChest(10, 10, 10);
        Controller.getPlayer().setHealth(100);
        redChest.cursePlayer();
        assertTrue("message", Controller.getPlayer().getCurrentHealth() > 0);
    }
}
