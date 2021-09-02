package tests.M5;

import controller.Controller;
import gamefiles.Inventory;
import gamefiles.items.HastePotion;
import gamefiles.items.RagePotion;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;


import java.util.concurrent.TimeUnit;
import java.io.IOException;


public class PotionTests extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testPotionEffectDurations() throws IOException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");
        RagePotion ragePotion = new RagePotion(1, "no", "no", 1, false, 600);
        HastePotion hastePotion = new HastePotion(2, "no", "no", 1, false, 600);

        Inventory.getHotbar()[0] = ragePotion;
        Inventory.getHotbar()[1] = hastePotion;
        press(KeyCode.DIGIT1);
        press(KeyCode.DIGIT2);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        assertEquals(40, Controller.getPlayer().getDamageStat(), .01);
        assertEquals(20, Controller.getPlayer().getSpeed(), .01);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(20, Controller.getPlayer().getDamageStat(), .01);
        assertEquals(10, Controller.getPlayer().getSpeed(), .01);
    }
    @Test
    public void testHastePotionDiagonalSpeed() throws InterruptedException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");

        HastePotion hastePotion = new HastePotion(2, "no", "no", 1, false, 600);
        Inventory.getHotbar()[0] = hastePotion;
        press(KeyCode.DIGIT1);

        press(KeyCode.A, KeyCode.W);
        assertEquals(20.0 / Math.sqrt(2), Controller.getPlayer().getCurrSpeed(), 0.1);
        release(KeyCode.A, KeyCode.W);
    }
}
