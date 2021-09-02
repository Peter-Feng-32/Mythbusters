package gamefiles.weapons;

public class Spear extends Weapon {

    public Spear(int id, String name, String description, double speed, double damage) {

        super(id, name, description, speed, damage);
        updateImageView("sprites/itemAssets/Spear.png");

    }
}
