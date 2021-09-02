package gamefiles.items;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Inventory;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class DroppedWeapon extends DroppedItem {
   
    public DroppedWeapon(Item item) {
        super(item);
        setWidth(75);
        setHeight(75);
    }

    @Override
    public void pickup() {
        if (!getDropped()) {
            setDropped(true);
            if (!atQueue.isEmpty()) {
                AnimationTimer e = atQueue.remove();
                e.stop();
            }
            displayReward("You picked up a " + getItem().getName());
            Inventory.addToInventory(getItem());

            Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().remove(this.getGroup());
                Controller.getCurrentRoom().removeDrop(this);
                getGroup().getChildren().removeAll(getImageView());
            });
        }
    }
}
