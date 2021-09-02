package gamefiles.rooms;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class BasicRoom extends Room {

    public BasicRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }


    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        Controller.getGameScreen().changeBackground("-fx-background-image: url('sprites/gameplayBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        return roomGroup;
    }

    // This will later have methods to generate monsters, gold, chests, and the like.
    public String toString() {
        return "Regular Room";
    }
}
