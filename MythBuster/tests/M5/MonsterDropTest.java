package tests.M5;

import controller.Controller;
import gamefiles.characters.Soldier;
import gamefiles.rooms.Room;
import gamefiles.weapons.WeaponDatabase;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;

public class MonsterDropTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void checkWeaponTrue() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        Room r = Controller.getCurrentRoom();
        Soldier s = r.spawnSoldier();

        assertEquals(true, s.checkWeapon(Controller.getPlayer().getWeapon()));

    }

    @Test
    public void checkWeaponFalse() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("Begin!");

        Room r = Controller.getCurrentRoom();
        Soldier s = r.spawnSoldier();

        assertEquals(false, s.checkWeapon(WeaponDatabase.getWeapon(2)));

    }
    
}
