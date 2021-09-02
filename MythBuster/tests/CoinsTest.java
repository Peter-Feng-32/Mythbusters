package tests;

import controller.Controller;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class CoinsTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    @Test
    public void testEasyCoinsDisplay() {
        clickOn("#HeroNameTextField");
        write("this is a valid name");
        clickOn("Begin!");
        verifyThat("#coinDisplay", TextMatchers.hasText("Coins: 30"));
    }

    @Test
    public void testMediumCoinsStored() {
        clickOn("#HeroNameTextField");
        write("this is a valid name");
        clickOn("#difficultySelector");
        clickOn("MEDIUM");
        clickOn("Begin!");
        verifyThat("#coinDisplay", TextMatchers.hasText("Coins: 20"));
    }
}
