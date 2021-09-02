package gamefiles.rooms;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class StartingRoom extends Room {
    public StartingRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        this.setLeftDoor(null);
        this.setBottomDoor(null);
        this.setRightDoor(null);
        this.setTopDoor(null);


    }

    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        Controller.getGameScreen().changeBackground("-fx-background-image: url('sprites/gameplayBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        return roomGroup;
    }

    public String toString() {
        return "Starting Room";
    }



}
