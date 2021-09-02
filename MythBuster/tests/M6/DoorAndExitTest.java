package tests.M6;

import controller.Controller;
import gamefiles.Door;
import gamefiles.rooms.BasicRoom;
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

public class DoorAndExitTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
        Controller.goToWinScreen();
    }

    @Test
    public void testLockedDoorImage() {
        Door testDoor = new Door(0, 0, new BasicRoom(0, 0, 0, 0));
        int doorWidth = 60;
        int doorHeight = 80;
        String unlockedImage = "sprites/door2.png";
        String lockedImage = "sprites/locked_door2.png";
        ImageView imageView = new ImageView(unlockedImage);
        imageView.setFitWidth(doorWidth);
        imageView.setFitHeight(doorHeight);
        testDoor.unlock();
        for (int i = 0; i < doorWidth; i++) {
            for (int j = 0; j < doorHeight; j++) {
                assertEquals(imageView.getImage().getPixelReader().getArgb(i, j), testDoor.getImageView().getImage().getPixelReader().getArgb(i, j));
            }
        }
        testDoor.lock();
        imageView = new ImageView(lockedImage);
        for (int i = 0; i < doorWidth; i++) {
            for (int j = 0; j < doorHeight; j++) {
                assertEquals(imageView.getImage().getPixelReader().getArgb(i, j), testDoor.getImageView().getImage().getPixelReader().getArgb(i, j));
            }
        }
    }

    @Test
    public void testExit() {
        verifyThat("Congratulations!", NodeMatchers.isNotNull());
        clickOn("Exit");
        try {
            verifyThat("Congratulations!", NodeMatchers.isNull());
        } catch (EmptyNodeQueryException e) {
            assertTrue(true);
        }
    }
}
