package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import controller.SpriteAnimation;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Boss extends Monster {
    Player player = Controller.getPlayer();
    Image idleImage = new Image("sprites/Boss/idle2.png");
    Image attackImage = new Image("sprites/Boss/attacking.png");
    Image deathImage = new Image("sprites/Boss/death.png");
    Image summonImage = new Image("sprites/Boss/summon.png");
    Image skillImage = new Image("sprites/Boss/skill1.png");
    SpriteAnimation idleAnimation = new SpriteAnimation(imageView, Duration.millis(750), 8, 4,0, 0, 100, 100);
    SpriteAnimation attackAnimation = new SpriteAnimation(imageView, Duration.millis(750), 13, 6, 0, 0,
            100, 100);
    SpriteAnimation deathAnimation = new SpriteAnimation(imageView, Duration.millis(1000), 18, 10, 0 ,0 , 100, 100);
    SpriteAnimation summonAnimation = new SpriteAnimation(imageView, Duration.millis(750), 5, 4, 0, 0, 100, 100);
    SpriteAnimation skillAnimation = new SpriteAnimation(imageView, Duration.millis(1000), 12, 6, 0, 0, 100, 100);
    private int damage = 150;
    private int damageCooldown = 45;
    private int summonCooldown = 100;
    private int skillCooldown = 300;
    private int skillDuration = 450;
    private boolean cursed = false;
    public Boss() {
        super("The Boss", 500 + (250 * Controller.getDifficulty()), 2.5, "sprites/Boss/idle.png", 250, 250);
        this.movementSpeed = 2.5;
        imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
        imageView.setId("boss");
        healthBarBacking.setY(positionY - 10);
        healthBar.setY(positionY - 10);
        idle();
    }

    @Override
    public void update() {
        double targetPositionX = Controller.getPlayer().getPositionX();
        double targetPositionY = Controller.getPlayer().getPositionY() - 30;
        checkDeath();
        if (isDead) {

            idleAnimation.stop();
            attackAnimation.stop();
            imageView.setImage(deathImage);
            deathAnimation.play();
            deathAnimation.setOnFinished(actionEvent -> Platform.runLater(Controller::goToWinScreen));
        }
        redrawHealthBar();
        // flip sprite if needed
        double offsetX = targetPositionX - (positionX + width / 2);
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
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Controller.getPlayer().intersects(this)) {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                damageCooldown = 45;
                attack();
                Controller.getPlayer().takeDamage(damage);
            }
        } else if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            damageCooldown = 45;
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        }

        if (summonCooldown <= 0 && idleAnimation.getStatus() == Animation.Status.RUNNING) {
            damageCooldown = 45;
            summonCooldown = 600;
            summon();
        } else {
            summonCooldown--;
        }
        if (skillCooldown <= 0 && idleAnimation.getStatus() == Animation.Status.RUNNING) {
            damageCooldown = 45;
            skillDuration = 450;
            skillCooldown = 900;
            cursed = true;
            displayReward("You've been cursed!");
            skill();
        } else {
            skillCooldown--;
        }
        if (cursed) {
            if (skillDuration <= 0) {
                player.setDamageBuffModifier(player.getDamageBuffModifier() + 0.75);
                player.setSpeedBuffModifier(player.getSpeedBuffModifier() + 0.5);
                cursed = false;
                displayReward("The curse expired");
            } else {
                skillDuration--;
            }
        }
    }

    private void skill() {
        idleAnimation.stop();
        imageView.setImage(skillImage);
        skillAnimation.setCycleCount(1);
        skillAnimation.play();
        player.setDamageBuffModifier(player.getDamageBuffModifier() - 0.75);
        player.setSpeedBuffModifier(player.getSpeedBuffModifier() - 0.5);
        skillAnimation.setOnFinished(actionEvent -> {
            skillAnimation.stop();
            imageView.setImage(idleImage);
            imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
            idleAnimation.play();
        });
    }

    public void idle() {
        imageView.setImage(idleImage);
        idleAnimation.setCycleCount(Animation.INDEFINITE);
        idleAnimation.play();
    }
    public void attack() {
        idleAnimation.stop();
        imageView.setImage(attackImage);
        attackAnimation.setCycleCount(1);
        attackAnimation.play();
        attackAnimation.setOnFinished(actionEvent -> {
            attackAnimation.stop();
            imageView.setImage(idleImage);
            imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
            idleAnimation.play();
        });
    }
    public void summon() {
        idleAnimation.stop();
        imageView.setImage(summonImage);
        summonAnimation.setCycleCount(1);
        summonAnimation.play();
        Monster minion1 = new BossMinion(Math.random() * (Controller.getW() - width), Math.random() * (Controller.getH() - height));
        GameLoop.getMonsters().add(minion1);
        Platform.runLater(() -> {
            Controller.getGameScreen().getBoard().getChildren().add(minion1.getGroup());
            Controller.getCurrentRoom().getMonsters().add(minion1);
        });
        summonAnimation.setOnFinished(actionEvent -> {
            summonAnimation.stop();
            imageView.setImage(idleImage);
            imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
            idleAnimation.play();
        });
    }
}
