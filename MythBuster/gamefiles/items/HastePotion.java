package gamefiles.items;

import controller.Controller;
import gamefiles.characters.Player;

public class HastePotion extends Consumable {

    // String name = "Haste Potion";
    // String description = "Doubles your movement speed for 10 seconds.";
    // int quantity = 0;
    // long duration = 600;
    private boolean doubled;
    private double oldSpeed;
    private double change;

    public HastePotion(int id, String name, String description, 
        int quantity, boolean active, long duration) {
        super(id, name, description, quantity, active, duration);
        updateImageView("sprites/itemAssets/hastePotion.png");
    }

    public void effect(long currentNanoTime) {
        use();
        
        if (!doubled) {
            Player player = Controller.getPlayer();
            player.setSpeedBuffModifier(player.getSpeedBuffModifier() + 0.75);

            doubled = true;
            //updateImageView("sprites/itemAssets/hastePotionConsumed.png");
            player.updateHotbarImages();
        }

        boolean updated = update();
        
        if (!updated) {
            Player player = Controller.getPlayer();
            player.setSpeedBuffModifier(player.getSpeedBuffModifier() - 0.75);
            doubled = false;
        }
    }
}
