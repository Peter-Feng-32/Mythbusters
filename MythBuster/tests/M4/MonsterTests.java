package tests.M4;

import controller.Controller;
import controller.GameLoop;
import gamefiles.characters.Monster;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;


public class MonsterTests extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testMonstersExist() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        int i = 0;
        while (i == 0) {
            for (Monster monster : GameLoop.getMonsters()) {
                i++;
            }
            if (i == 0) {
                press(KeyCode.D); //Move to the room to the right.
            }
        }
        assertNotEquals(0, i);


    }
    @Test
    public void testMonsterDamage() throws InterruptedException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        int i = 0;
        while (i == 0) {
            for (Monster monster : GameLoop.getMonsters()) {
                i++;
            }
            if (i == 0) {
                press(KeyCode.D); //Move to the room to the right.
            }
        }

        for (Monster monster : GameLoop.getMonsters()) {
            double health = monster.getCurrentHealth();
            monster.takeDamage(10);
            assertNotEquals(health, monster.getCurrentHealth());
        }


    }

}
