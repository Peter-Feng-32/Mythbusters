package gamefiles.weapons;

public class Sword extends Weapon {

    public Sword(int id, String name, String description, double speed, double damage) {

        super(id, name, description, speed, damage);
        updateImageView("sprites/itemAssets/Sword.png");

    }
}
