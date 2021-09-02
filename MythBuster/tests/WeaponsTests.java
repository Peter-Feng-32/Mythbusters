package tests;

import controller.Controller;
import gamefiles.characters.Player;
import views.GameScreen;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class WeaponsTests extends ApplicationTest {

    private Controller controller;
    private Player player;
    private GameScreen gameScreen;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        controller.goToConfigurationScreen();
    }

    @Test
    public void testWeaponSelection() {
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");
        verifyThat("#weaponDisplay", TextMatchers.hasText("Weapon: Shield"));
    }

    @Test
    public void testSwordPlayerAndDisplay() {
        clickOn("#HeroNameTextField");
        write("Test");

        String startingWeapon = "SWORD";

        clickOn("#startingWeaponSelector");
        clickOn(startingWeapon);
        clickOn("Begin!");

        player = controller.getPlayer();
        gameScreen = controller.getRoomOne();

        assertEquals("Sword", player.getWeapon().getName());
        assertEquals("Weapon: Sword", gameScreen.getWeaponDisplay().getText());
    }
}
