package gamefiles.weapons;

import java.util.HashMap;
import java.util.Map;

public class WeaponDatabase {
    private static Map<Integer, Weapon> weapons = new HashMap<>();

    /**
     * Create a weapon database.
     */

    public static void initialize() {
        weapons.put(0, new Spear(1, "Spear", "This is a spear.", 1, 2));
        weapons.put(1, new Sword(0, "Sword", "This is a sword.", 0.85, 2.5));
        weapons.put(2, new Bow(2, "Bow", "This is a bow.", 1, 1.5));
    }

    /**
     * Get a weapon with an id from the database.
     *
     * @param key the id of the item to get
     * @return the weapon from the passed in id
     */
    public static Weapon getWeapon(int key) {
        return weapons.get(key);
    }
}
