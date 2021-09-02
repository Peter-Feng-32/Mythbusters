package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import controller.SpriteAnimation;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Mage extends Monster {

    SpriteAnimation fireballAnimation = new SpriteAnimation(imageView, Duration.millis(750), 10, 3, 0, 0, 78, 79);
    public Mage() {
        super("Mage Enemy", 100, 5, "sprites/wizard.png", 150, 150);
        imageView.setViewport(new Rectangle2D(0, 0, 78, 79));
    }

    public Mage(double health, double movementSpeed, String spritePath, 
                double width, double height) {
        super("Mage Enemy", health, movementSpeed, spritePath, width, height);
        imageView.setViewport(new Rectangle2D(0, 0, 78, 79));

    }

    private int spawnFireballCooldown = 15;
    private double targetPositionX = Math.random() * (Controller.getW() - width);
    private double targetPositionY = Math.random() * (Controller.getH() - height);


    public void update() {
        // game logic
        checkDeath();
        redrawHealthBar();
        // move
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        } else {
            if (spawnFireballCooldown > 0) {
                spawnFireballCooldown--;
            } else {
                //Shoot a fireball.
                fireballAnimation.setCycleCount(1);
                fireballAnimation.play();
                fireballAnimation.setOnFinished(actionEvent -> {
                    fireballAnimation.stop();
                    Fireball fireball = new Fireball(positionX, positionY);
                    imageView.setViewport(new Rectangle2D(0, 0, 78, 79));
                    Platform.runLater(() -> Controller.getGameScreen().getBoard().getChildren().add(fireball.getGroup()));
                    GameLoop.getMonsters().add(fireball);
                    spawnFireballCooldown = 500;
                    targetPositionX = Math.random() * (Controller.getW() - width);
                    targetPositionY = Math.random() * (Controller.getH() - height);
                });
            }
        }
    }
}
