package gamefiles.items;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class DroppedCoin extends DroppedItem {

    public DroppedCoin(Item item) {
        super(item);
        setWidth(25);
        setHeight(25);
    }

    @Override
    public void pickup() {
        if (!getDropped()) {
            setDropped(true);
            if (!atQueue.isEmpty()) {
                AnimationTimer e = atQueue.remove();
                e.stop();
            }
            displayReward("You picked up " + getItem().getQuantity() + " coins");
            Controller.getPlayer().addCoins(getItem().getQuantity());

            Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().remove(this.getGroup());
                Controller.getCurrentRoom().removeDrop(this);
                getGroup().getChildren().removeAll(getImageView());
            });
        }
    }
}
