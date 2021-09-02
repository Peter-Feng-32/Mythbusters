package gamefiles.weapons;

import gamefiles.items.Item;

public abstract class Weapon extends Item {
    protected double speed;
    protected double damage;

    /**
     * Create new weapon object with initial name, speed, and damage.
     * @param id            the id of the weapon
     * @param description   the description of the weapon
     * @param name          the name of the weapon
     * @param speed         the speed of the weapon
     * @param damage        the damage of the weapon
     */
    public Weapon(int id, String name, String description, double speed, double damage) {
        super(id, name, description, 1);
        this.speed = speed;
        this.damage = damage;
    }


    public double getDamage() {
        return this.damage;
    }

    public boolean equals(Weapon w) {
        return this.getName() == w.getName();
    }

    public double getSpeed() {
        return speed;
    }
}
