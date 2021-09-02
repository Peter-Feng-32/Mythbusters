package tests.M6;

import controller.Controller;
import controller.GameLoop;
import controller.SpriteAnimation;
import gamefiles.Door;
import gamefiles.characters.Boss;
import gamefiles.characters.Monster;
import gamefiles.rooms.BasicRoom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class BossTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testBossHealthHard() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#difficultySelector");
        clickOn("HARD");
        clickOn("Begin!");

        Boss b = new Boss();
        assertEquals(1000, b.getCurrentHealth(), 0.01);
    }

    @Test
    public void testBossHealthEasy() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#difficultySelector");
        clickOn("EASY");
        clickOn("Begin!");

        Boss b = new Boss();
        assertEquals(500, b.getCurrentHealth(), 0.01);
    }

    @Test
    public void testBossExists() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("Begin!");
        Platform.runLater(Controller::goToBossRoom);
        Platform.runLater(() -> verifyThat("#boss", NodeMatchers.isNotNull()));
    }

    @Test
    public void testBossDoorsLocked() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("Begin!");
        Platform.runLater(() -> {
            Controller.goToBossRoom();
            for (Door door : Controller.getCurrentRoom().getDoors()) {
                if (door != null) {
                    assertTrue(door.isLocked());
                }
            }
        });
    }

//    @Test
//    this doesn't actually work xd
//    public void testBossWin() {
//        clickOn("Start Game");
//        clickOn("#HeroNameTextField");
//        write("test123");
//        clickOn("Begin!");
//        Platform.runLater(() -> {
//            Controller.goToBossRoom();
//            Monster boss = Controller.getCurrentRoom().getMonsters().get(0);
//            boss.takeDamage(1000);
//
//        });
//        Timeline deathDelay = new Timeline(
//                new KeyFrame(Duration.seconds(2),
//                        event -> verifyThat("Congratulations", NodeMatchers.isNotNull())));
//        deathDelay.setCycleCount(1);
//        deathDelay.play();
//        verifyThat("Congratulations", NodeMatchers.isNotNull());
//    }
    
}
