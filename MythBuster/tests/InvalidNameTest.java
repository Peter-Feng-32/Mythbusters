package tests;
import controller.Controller;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;


public class InvalidNameTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
        controller.goToConfigurationScreen();
    }

    @Test
    public void testInvalidName() {
        clickOn("Begin!");
        verifyThat("Warning", NodeMatchers.isVisible());
    }
}
