package gamefiles;

import controller.Controller;
import javafx.animation.AnimationTimer;

public class GreenTreasureChest extends TreasureChest {
    public GreenTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestGreen.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        Controller.getPlayer().setHealth(Controller.getPlayer().getMaximumHealth());
        if (!atQueue.isEmpty()) {
            AnimationTimer e = atQueue.remove();
            e.stop();
        }
        displayReward("Your health is fully restored!");

        opened = true;
    }
}
