package gamefiles.rooms;

import java.util.ArrayList;

import controller.Controller;
import gamefiles.BlueTreasureChest;
import gamefiles.GreenTreasureChest;
import gamefiles.RedTreasureChest;
import gamefiles.YellowTreasureChest;
import gamefiles.TreasureChest;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;

public class TreasureRoom extends Room {

    private int treasureCount = (int) (Math.random() * 3) + 1;
    private ArrayList<TreasureChest> treasureChests = new ArrayList<>();
    private static AnimationTimer animationTimer;

    private Group treasureGroup;
    public TreasureRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        switch (treasureCount) {
        case 1:
            addRandomChest(width / 2 - 50, height / 2 - 50);
            break;
        case 2:
            addRandomChest(width / 2 - 150, height / 2 - 50);
            addRandomChest(width / 2 + 50, height / 2 - 50);
            break;
        case 3:
            addRandomChest(width / 2 - 200, height / 2 - 50);
            addRandomChest(width / 2 - 50, height / 2 - 50);
            addRandomChest(width / 2 + 100, height / 2 - 50);
            break;
        default:
            addRandomChest(width / 2 - 50, height / 2 - 50);
            break;
        }
    }

    public void addRandomChest(double positionX, double positionY, int cost) {
        int random = (int) (Math.random() * 4);
        switch (random) {
        case 0:
            treasureChests.add(new RedTreasureChest(positionX, positionY, cost));
            break;
        case 1:
            treasureChests.add(new BlueTreasureChest(positionX, positionY, cost));
            break;
        case 2:
            treasureChests.add(new YellowTreasureChest(positionX, positionY, cost));
            break;
        case 3:
            treasureChests.add(new GreenTreasureChest(positionX, positionY, cost));
            break;
        default:
            treasureChests.add(new RedTreasureChest(positionX, positionY, cost));
            break;
        }
    }
    public void addRandomChest(double positionX, double positionY) {
        addRandomChest(positionX, positionY, (int) (Math.random() * 5 + 2) * 5);
    }


    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        treasureGroup = new Group();
        for (TreasureChest treasureChest : treasureChests) {
            if (!treasureChest.isOpened()) {
                treasureGroup.getChildren().add(treasureChest.getGroup());
            }
        }

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for (TreasureChest treasureChest : treasureChests) {
                    if (treasureChest.canOpen()) {
                        treasureChest.open();
                        treasureGroup.getChildren().remove(treasureChest.getGroup());
                    }
                }
            }
        };
        animationTimer.start();

        roomGroup.getChildren().add(treasureGroup);
        Controller.getGameScreen().changeBackground("-fx-background-image: url('sprites/treasureBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        return roomGroup;
    }
    public String toString() {
        return "Treasure Room";
    }
    public ArrayList<TreasureChest> getTreasureChests() {
        return treasureChests;
    }
    public static AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}
