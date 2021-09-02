package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.geometry.Rectangle2D;

public class Trap extends Monster {

    private static int trapCount;

    public Trap(double posX, double posY) {
        super("Trap", 10, 0, "sprites/Trap.png", 50, 50);
        imageView.setViewport(new Rectangle2D(0, 0, 100, 95));
        imageView.setRotate(45);
        moveAbsolute(posX, posY);
        trapCount++;
    }


    private int damage = 25;

    public void update() {
        // game logic
        checkDeath();
        redrawHealthBar();
        if (Controller.getPlayer().intersects(this)) {
            Controller.getPlayer().takeDamage(damage);
            currentHealth = 0;
            checkDeath();
        }
        if (GameLoop.getMonsters().size() - trapCount == 0) {
            currentHealth = 0;
            checkDeath();
        }
        /*if (currentHealth <= 0) {
            trapCount--;
        }*/
    }

    public static void setTrapCount(int amount) {
        trapCount = amount;
    }
    public static void decrementTrapCount(int amount) {
        trapCount -= amount;
    }
    public static int getTrapCount() {
        return trapCount;
    }
}
