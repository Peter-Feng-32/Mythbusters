package gamefiles.characters;

import controller.Controller;

public class Soldier extends Monster {

    public Soldier() {
        this(100, 5, "sprites/Soldier/FF10WarriorR.png", 100, 100);
    }

    public Soldier(double health, double movementSpeed, String spritePath, 
                    double width, double height) {
        super("Soldier Enemy", health, movementSpeed, spritePath, width, height);

    }

    private int damage = 100;
    private int damageCooldown = 50;

    public void update() {
        double targetPositionX = Controller.getPlayer().getPositionX();
        double targetPositionY = Controller.getPlayer().getPositionY() - 30;
        // game logic

        checkDeath();
        redrawHealthBar();
        // flip sprite if needed
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        if (offsetX > 0) {
            if (Math.abs(offsetX) > 10) {
                imageView.setScaleX(1);
            }
        } else {
            if (Math.abs(offsetX) > 10) {
                imageView.setScaleX(-1);
            }
        }

        //Damage player or move.
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Controller.getPlayer().intersects(this)) {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                damageCooldown = 50;
                Controller.getPlayer().takeDamage(damage);
            }
        } else if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            damageCooldown = 50;
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        }
    }
}

