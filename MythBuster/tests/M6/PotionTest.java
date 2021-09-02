package tests.M6;

import controller.Controller;
import controller.GameLoop;
import controller.SpriteAnimation;
import gamefiles.Door;
import gamefiles.Inventory;
import gamefiles.characters.Boss;
import gamefiles.characters.Monster;
import gamefiles.items.ItemDatabase;
import gamefiles.rooms.BasicRoom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class PotionTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }



    @Test
    public void testUsePotionModifier() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#difficultySelector");
        clickOn("EASY");
        clickOn("Begin!");
        Inventory.addToInventory(ItemDatabase.getItem(1));
        push(KeyCode.I);
        Platform.runLater(() -> Inventory.addToHotbar(0, 0));
        push(KeyCode.I);
        push(KeyCode.DIGIT1);
        assertEquals(Controller.getPlayer().getDamageBuffModifier(), 2.0, 0.01);
    }


    @Test
    public void testPotionUI() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#difficultySelector");
        clickOn("EASY");
        clickOn("Begin!");
        Inventory.addToInventory(ItemDatabase.getItem(1));
        push(KeyCode.I);
        Platform.runLater(() -> Inventory.addToHotbar(0, 0));
        push(KeyCode.I);
        push(KeyCode.DIGIT1);
        verifyThat("9", NodeMatchers.isNotNull()); //Test that the timer is there.

    }



}
