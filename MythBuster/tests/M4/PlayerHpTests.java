package tests.M4;

import controller.Controller;
import controller.GameLoop;
import gamefiles.characters.Player;
import gamefiles.characters.Monster;
import gamefiles.Heart;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerHpTests extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testStartWithFullHeartsAnd250Hp() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("Test name");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        GameLoop.setMonsters(new ArrayList<Monster>());

        Player player = Controller.getPlayer();
        
        assertEquals(300, player.getCurrentHealth(), .1);

        ArrayList<Heart> hearts = player.getHearts();
        for (Heart heart : hearts) {
            assertTrue(heart.isFull());
        }
    }

    @Test
    public void testReducePlayerHpBy100() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        GameLoop.setMonsters(new ArrayList<Monster>());

        Player player = Controller.getPlayer();

        player.takeDamage(100);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(200, player.getCurrentHealth(), .1);

        ArrayList<Heart> hearts = player.getHearts();
        for (int i = 0; i < hearts.size(); i++) {
            if (i > 3) {
                assertFalse(hearts.get(i).isFull());
            } else {
                assertTrue(hearts.get(i).isFull());
            }
        }
    }

}
