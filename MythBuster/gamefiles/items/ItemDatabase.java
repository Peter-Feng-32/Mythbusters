package gamefiles.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class ItemDatabase {
    private static Map<Integer, Item> items = new HashMap<>();

    /**
     * Create an item database.
     */
    public static void initialize() {
        items.put(0, new HealthPotion(0, "Health Potion", 
            "Restores 2 hearts.", 0, false, 0));
        items.put(1, new RagePotion(0, "Rage Potion", 
            "Doubles your attack output for 10 seconds.", 0, false, 600));
        items.put(2, new HastePotion(0, "Haste Potion", 
            "Doubles your movement speed for 10 seconds.", 1, false, 600));
    }

    /**
     * Get an item with an id from the database.
     *
     * @param key the id of the item to get
     * @return the item from the passed in id
     */
    public static Item getItem(int key) {
        if (key == -1) {
            return new Coin(-1, "Coin", "Money", 0);
        } else if (key == 0) {
            return new HealthPotion(0, "Health Potion", "Restores 2 hearts.", 0, false, 0);
        } else if (key == 1) {
            return new RagePotion(0, "Rage Potion", 
                "Doubles your attack output for 10 seconds.", 0, false, 600);
        } else if (key == 2) {
            return new HastePotion(0, "Haste Potion", 
                "Doubles your movement speed for 10 seconds.", 0, false, 600);
        }
        return new RagePotion(0, "Rage Potion", 
            "Doubles your attack output for 10 seconds.", 0, false, 600);
    }
    
    public static void resetQuantities() {
        Collection<Item> currItems = items.values();
        for (Item item : currItems) {
            item.subtractQuantity(item.getQuantity());
        }
    }
}
