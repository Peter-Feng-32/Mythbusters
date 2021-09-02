package tests.M6;

import controller.Controller;
import gamefiles.Door;
import gamefiles.characters.Boss;
import gamefiles.rooms.BasicRoom;
import gamefiles.rooms.ChallengeRoom;
import gamefiles.rooms.Room;
import gamefiles.rooms.RoomLayout;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class ChallengeRoomTest extends ApplicationTest {

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testZeroMonsters() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#difficultySelector");
        clickOn("HARD");
        clickOn("Begin!");

        RoomLayout layout = controller.getRoomLayout();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (layout.getRoom(i, j) instanceof ChallengeRoom) {
                    controller.setCurrentRoom(layout.getRoom(i, j));
                    break;
                }
            }
        }

        Room currRoom = Controller.getCurrentRoom();

        assertEquals(0, currRoom.getMonsters().size());

    }
    
}