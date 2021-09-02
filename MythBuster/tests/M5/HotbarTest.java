package tests.M5;

import controller.Controller;
import gamefiles.Inventory;

import gamefiles.items.ItemDatabase;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class HotbarTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }
    
    @Test
    public void testInventorySize() {
        Inventory.addToInventory(ItemDatabase.getItem(0));
        Inventory.addToInventory(ItemDatabase.getItem(1));
        Inventory.addToInventory(ItemDatabase.getItem(2));

        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        push(KeyCode.I);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Inventory.addToHotbar(0, 0);
                assertEquals(2, Inventory.getInventory().size());
                assertEquals(1, Inventory.getHotbarSize());
            }
        });
    }



}
