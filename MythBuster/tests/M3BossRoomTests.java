package tests;

import controller.Controller;
import gamefiles.weapons.WeaponDatabase;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class M3BossRoomTests extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

        //Set up test player so we don't get null pointers in required attributes.
        controller.getPlayer().setName("Test Player");
        controller.getPlayer().setCoins(30);
        controller.getPlayer().setWeapon(WeaponDatabase.getWeapon(0));
        Controller.goToStartingRoom();
        Controller.goToBossRoom();
    }



    @Test
    public void testBossRoomExists() {
        verifyThat("The Boss", NodeMatchers.isNotNull());
    }
    @Test
    public void testTransitionToWin() {
        verifyThat("The Boss", NodeMatchers.isNotNull());


        clickOn("The Boss");

        verifyThat("You Win!", NodeMatchers.isNotNull());

    }

}
