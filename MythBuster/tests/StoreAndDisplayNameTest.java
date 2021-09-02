package tests;

import controller.Controller;
import gamefiles.characters.Player;

import javafx.stage.Stage;
import views.GameScreen;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class StoreAndDisplayNameTest extends ApplicationTest {
    private Controller controller;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        controller.goToConfigurationScreen();
    }

    /**
     * Make sure that when the user begin the game, the name is actually
     * stored into the Player object.
     */
    @Test
    public void playerNameStoredCorrectly() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        assertEquals("Test", player.getName());
    }

    /**
     * Make sure that when the user begin the game, the name displayed
     * is correct with the name of the player in the system.
     */
    @Test
    public void playerNameDisplayedCorrectly() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        GameScreen gameScreen = controller.getRoomOne();
        assertEquals("Name: " + player.getName(), gameScreen.getNameDisplay().getText());
    }
}
