package tests;
import controller.Controller;

import gamefiles.characters.Player;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;




public class StartGameButtonTest extends ApplicationTest {

    private Controller controller;
    private Player player;




    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testStartGame() {
        clickOn("Start Game");
        verifyThat("Configuration Screen", NodeMatchers.isNotNull());
    }

    @Test
    public void testInGame() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("Test");
        clickOn("Begin!");

        player = controller.getPlayer();

        verifyThat("Name: " + player.getName(), NodeMatchers.isNotNull());
        verifyThat("Weapon: " + player.getWeapon().getName(), NodeMatchers.isNotNull());
        verifyThat("Coins: " + player.getCoins(), NodeMatchers.isNotNull());
    }

}
