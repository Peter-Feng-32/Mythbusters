package gamefiles.rooms;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class BossRoom extends Room {

    public BossRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }

    @Override
    public Group getRoomGroup() {
        Controller.getGameScreen().changeBackground("-fx-background-image: url('sprites/bossBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        return super.getRoomGroup();
    }
    public String toString() {
        return "Boss Room";
    }
}
