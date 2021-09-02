package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import controller.SpriteAnimation;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class BossMinion extends Monster {
    Image idleImage = new Image("sprites/Boss/summonIdle.png");
    Image deathImage = new Image("sprites/Boss/summonDeath.png");
    SpriteAnimation spawnAnimation = new SpriteAnimation(imageView, Duration.millis(750), 6, 3, 0, 0, 50, 50);
    SpriteAnimation idleAnimation = new SpriteAnimation(imageView, Duration.millis(750), 4, 4, 0, 0, 50, 50);
    SpriteAnimation deathAnimation = new SpriteAnimation(imageView, Duration.millis(750), 5, 3, 0, 0, 50, 50);
    private double targetPositionX = Math.random() * (Controller.getW() - width);
    private double targetPositionY = Math.random() * (Controller.getH() - height);
    private int damageCooldown = 10;
    private int damage = 50;

    public BossMinion(double posX, double posY) {
        super("Boss Minion", 5, 5, "sprites/Boss/summonAppear.png", 100, 100);
        moveAbsolute(posX, posY);
        imageView.setViewport(new Rectangle2D(0, 0, 50, 50));
        spawnAnimation.play();
        spawnAnimation.setOnFinished(actionEvent -> {
            spawnAnimation.stop();
            imageView.setImage(idleImage);
            imageView.setViewport(new Rectangle2D(0, 0, 50, 50));
            idleAnimation.setCycleCount(10000);
            idleAnimation.play();
        });
    }

    @Override
    public void update() {
        // game logic
        checkDeath();
        if (isDead) {
            idleAnimation.stop();
            imageView.setImage(deathImage);
            deathAnimation.play();
            deathAnimation.setOnFinished(actionEvent -> Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().remove(this.getGroup());
                Controller.getCurrentRoom().getMonsters().remove(this);
                monsterGroup.getChildren().removeAll(imageView, healthBar, healthBarBacking);
            }));
        }
        redrawHealthBar();
        // move
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);

        if (Controller.getPlayer().intersects(this)) {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                damageCooldown = 10;
                Controller.getPlayer().takeDamage(damage);
            }
        } else if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            damageCooldown = 10;
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        } else {
            targetPositionX = Math.random() * (Controller.getW() - width);
            targetPositionY = Math.random() * (Controller.getH() - height);
        }

    }
}
