package tests.M5;

import controller.Controller;
import gamefiles.Inventory;
import gamefiles.characters.Player;
import gamefiles.characters.Soldier;
import gamefiles.items.HealthPotion;
import gamefiles.rooms.Room;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class InventoryAndHealthTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    @Test
    public void testDropNotification() throws InterruptedException {
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");

        Room room = Controller.getCurrentRoom();
        Soldier s = room.spawnSoldier();
        Platform.runLater(new Runnable() {
            private boolean test;
            @Override
            public void run() {
                test = s.addItems();
                if (test) {
                    verifyThat("#dropNotificationDisplay", NodeMatchers.isNotNull());
                }
            }
        });

    }

    @Test
    public void testHealthRestore() {
        clickOn("#HeroNameTextField");
        write("test");
        clickOn("Begin!");
        Player player = Controller.getPlayer();
        player.takeDamage(100);
        HealthPotion h = new HealthPotion(1, "health potion", "why", 0, false, 0);
        Inventory.addToInventory(h);
        Platform.runLater(() -> {
            h.effect(100);
            assertEquals(300, player.getCurrentHealth(), 0.1);
        });
    }
}
