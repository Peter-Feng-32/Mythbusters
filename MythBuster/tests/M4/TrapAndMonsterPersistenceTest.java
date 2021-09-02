package tests.M4;

import controller.Controller;
import controller.GameLoop;
import gamefiles.characters.Fireball;
import gamefiles.characters.Mage;
import gamefiles.characters.Monster;
import gamefiles.characters.Trap;
import gamefiles.characters.TrapMonster;
import gamefiles.rooms.Room;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;


public class TrapAndMonsterPersistenceTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);

    }

    @Test
    public void testTrapRemovedWhenAllOtherMonsterDie() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("Begin!");
        Room currentRoom = Controller.getCurrentRoom();
        TrapMonster trapMonster = currentRoom.spawnTrapMonster();
        Platform.runLater(() -> {
            Controller.getGameScreen().getBoard().getChildren().add(trapMonster.getGroup());
        });
        ArrayList<Monster> monsters = GameLoop.getMonsters();
        assertNotEquals(monsters.size(), 0);
        for (int countdown = 5; countdown > 0; countdown--) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Controller.getPlayer().addHealth(500);
        }
        for (int i = monsters.size() - 1; i >= 0; i--) {
            if (!(monsters.get(i) instanceof Trap)) {
                monsters.get(i).loseAllHealth();
            }
        }
        for (int i = monsters.size() - 1; i >= 0; i--) {
            monsters.get(i).update();
        }
        assertEquals(0, monsters.size());
    }
    @Test
    public void testFireBallLimit() throws InterruptedException {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("Begin!");
        Room currentRoom = Controller.getCurrentRoom();
        ArrayList<Monster> monsters = GameLoop.getMonsters();
        Mage mage = currentRoom.spawnMage();
        Platform.runLater(() -> {
            Controller.getGameScreen().getBoard().getChildren().add(mage.getGroup());
        });
        int mageCount = 0;
        int fireballCount = 0;
        for (Monster monster: monsters) {
            if (monster instanceof Mage) {
                mageCount++;
            }
        }
        assertNotEquals(monsters.size(), 0);
        for (int countdown = 10; countdown > 0; countdown--) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Controller.getPlayer().addHealth(500);
            fireballCount = 0;
            for (Monster monster: monsters) {
                if (monster instanceof Fireball) {
                    fireballCount++;
                }
            }
            assertTrue(fireballCount <= mageCount);
        }
    }
}
