package tests;

import controller.Controller;
import gamefiles.characters.Player;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerMovementTest extends ApplicationTest {
    private Controller controller;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    /**
     * Make sure that pressing the correct buttons correspond to the correct direction of movement.
     */
    @Test
    public void oneDirectionalMovement() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        double previousPositionX = player.getPositionX();
        double previousPositionY = player.getPositionY();
        press(KeyCode.A);
        assertEquals(10, player.getSpeed(), 0.1);
        assertTrue(previousPositionX > player.getPositionX());
        release(KeyCode.A);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.W);
        assertEquals(10, player.getSpeed(), 0.1);
        assertTrue(previousPositionY > player.getPositionY());
        release(KeyCode.W);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.S);
        assertEquals(10, player.getSpeed(), 0.1);
        assertTrue(previousPositionY < player.getPositionY());
        release(KeyCode.S);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.D);
        assertEquals(10, player.getSpeed(), 0.1);
        assertTrue(previousPositionX < player.getPositionX());
        release(KeyCode.D);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();
    }

    /**
     * Make sure that if the player move in two directions at once, that the speed is reduced to 7.
     */
    @Test
    public void twoDirectionalMovement() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        double previousPositionX = player.getPositionX();
        double previousPositionY = player.getPositionY();
        press(KeyCode.A, KeyCode.W);
        assertEquals(7, player.getSpeed(), 0.1);
        assertTrue(previousPositionX > player.getPositionX());
        assertTrue(previousPositionY > player.getPositionY());
        release(KeyCode.A, KeyCode.W);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.A, KeyCode.S);
        assertEquals(7, player.getSpeed(), 0.1);
        assertTrue(previousPositionX > player.getPositionX());
        assertTrue(previousPositionY < player.getPositionY());
        release(KeyCode.A, KeyCode.S);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.D, KeyCode.W);
        assertEquals(7, player.getSpeed(), 0.1);
        assertTrue(previousPositionX < player.getPositionX());
        assertTrue(previousPositionY > player.getPositionY());
        release(KeyCode.D, KeyCode.W);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();

        press(KeyCode.D, KeyCode.S);
        assertEquals(7, player.getSpeed(), 0.1);
        assertTrue(previousPositionX < player.getPositionX());
        assertTrue(previousPositionY < player.getPositionY());
        release(KeyCode.D, KeyCode.S);
        previousPositionX = player.getPositionX();
        previousPositionY = player.getPositionY();
    }
}
