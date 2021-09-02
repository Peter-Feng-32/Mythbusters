package gamefiles.items;

import controller.Controller;
import gamefiles.characters.Player;

public class HealthPotion extends Consumable {

    // String name = "Health Potion";
    // String description = "Restores 2 hearts.";
    // int quantity = 0;
    // long duration = 0;
    
    public HealthPotion(int id, String name, String description, 
        int quantity, boolean active, long duration) {
        super(id, name, description, quantity, active, duration);
        updateImageView("sprites/itemAssets/healthPotion.png");
    }

    public void effect(long currentNanoTime) {
        this.use();
        Player player = Controller.getPlayer();
        if (player.getCurrentHealth() + 100 > player.getMaximumHealth()) {
            player.addHealth(player.getMaximumHealth() - player.getCurrentHealth());
        } else {
            player.addHealth(100);
        }
        player.updateHearts(player.getCurrentHealth());
        this.update();
    }
}
