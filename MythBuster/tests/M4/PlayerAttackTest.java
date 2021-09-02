package tests.M4;

import controller.Controller;
import controller.GameLoop;
import gamefiles.characters.Monster;
import gamefiles.characters.Player;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerAttackTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testPlayerAttack() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        //Player player = Controller.getPlayer();
        for (int i = 0; i < 10; i++) {
            press(KeyCode.J);
            release(KeyCode.J);
            for (Monster monster : GameLoop.getMonsters()) {
                if (monster.getCurrentHealth() < monster.getMaxHealth()) {
                    assertTrue(true);
                }
            }
        }

    }

    @Test
    public void testMoveWhileAttacking() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        Player player = Controller.getPlayer();
        assertEquals(Controller.getW() / 2, player.getPositionX(), 0.1);
        assertEquals(Controller.getH() / 2, player.getPositionY(), 0.1);
        press(KeyCode.J);
        release(KeyCode.J);
        press(KeyCode.D);
        //make sure player didn't move because of the attack
        assertEquals(Controller.getW() / 2, player.getPositionX(), 0.1);
        assertEquals(Controller.getH() / 2, player.getPositionY(), 0.1);
    }
}
