package tests.M6;

import controller.Controller;
import gamefiles.DropMethods;
import gamefiles.Inventory;
import gamefiles.characters.Player;
import gamefiles.items.DroppedCoin;
import gamefiles.items.DroppedItem;
import gamefiles.items.ItemDatabase;
import gamefiles.items.HastePotion;
import gamefiles.items.RagePotion;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;

import java.util.ResourceBundle.Control;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Arrays;

public class DropTests extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testDroppedCoins() throws IOException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");

        Player player = Controller.getPlayer();
        int oldCoins = player.getCoins();

        DropMethods.dropCoinsNotRandom(100, 10, 
                                        Controller.getW() / 2 + player.getWidth() / 2, 
                                        Controller.getH() / 2 + player.getHeight() / 2);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertEquals(oldCoins + 100, player.getCoins());
    }
    @Test
    public void testDroppedPotions() throws InterruptedException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");

        Player player = Controller.getPlayer();

        RagePotion ragePotion = new RagePotion(1, "no", "no", 1, false, 600);
        HastePotion hastePotion = new HastePotion(2, "no", "no", 1, false, 600);
        DroppedItem ragePotionDropped = new DroppedItem(ragePotion);
        DroppedItem hastePotionDropped = new DroppedItem(hastePotion);
        
        ragePotionDropped.drop(Controller.getW() / 2 + player.getWidth() / 2, 
                                Controller.getH() / 2 + player.getHeight() / 2, 
                                false);
        hastePotionDropped.drop(Controller.getW() / 2 + player.getWidth() / 2, 
                                Controller.getH() / 2 + player.getHeight() / 2, 
                                false);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(Arrays.asList(Inventory.getHotbar()).contains(ragePotion));
        assertTrue(Arrays.asList(Inventory.getHotbar()).contains(hastePotion));
    }
}
