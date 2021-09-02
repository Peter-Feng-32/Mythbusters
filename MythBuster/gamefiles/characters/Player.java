package gamefiles.characters;

import java.util.ArrayList;
import java.util.Optional;

import controller.Controller;
import gamefiles.Heart;
import controller.GameLoop;
import controller.SpriteAnimation;
import gamefiles.Inventory;
import gamefiles.Touchable;
import gamefiles.items.Consumable;
import gamefiles.items.HealthPotion;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.Bow;
import gamefiles.weapons.Spear;
import gamefiles.weapons.Sword;
import gamefiles.weapons.Weapon;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Player implements Touchable {
    private String name;
    private int coins;
    private Weapon weapon;
    private double speed = 10;
    private double currSpeed;
    private double speedBuffModifier = 1.0;
    private double speedWeaponModifier = 1.0;
    private int numHearts;
    private double maxHealth;
    private double currentHealth;
    private double attackCD = 0;
    private double moveCD = 0;
    private double damage = 10;
    private double damageBuffModifier = 1.0;
    private double damageWeaponModifier = 1.0;

    private double damageCooldown;
    private int maxActiveConsumables = 2;
    private Image swordSprite = new Image("sprites/Player/daggerPlayer.png");
    private Image spearSprite = new Image("sprites/Player/spearPlayer.png");
    private Image bowSprite = new Image("sprites/Player/bowPlayer.png");
    private ImageView imageView;
    private int direction = 0; //left = 0, right = 1
    private ArrayList<Consumable> activeConsumables = new ArrayList<>();

    private double positionX;
    private double positionY;
    private double width = 100;
    private double height = 100;

    private final double heartsPadding = 10;
    private final double heartsDimensions = 50;
    private final double hotbarPadding = 10;

    private int spriteWidth = 64;
    private int spriteHeight = 63;
    private int spriteX = 0;
    private int spriteY = 581;

    private Group imageGroup;
    private ArrayList<Heart> hearts;
    private HBox heartsBox;
    private Item[] hotbar;
    private HBox hotbarBox;

    private AnimationTimer playerLogic;
    private AnimationTimer playerHpUpdate;
    private AnimationTimer itemLoop;

    private static int greedyIndex;
    private static int potionsUsed;

    public Player(int coins, Weapon weapon) {
        this.coins = coins;
        this.weapon = weapon;
        speedWeaponModifier = weapon != null ? weapon.getSpeed() : 1;
        this.currSpeed = speed;

        damageWeaponModifier = weapon != null ? weapon.getDamage() : 1;
        imageView = new ImageView();
        if (weapon instanceof Spear) {
            imageView.setImage(spearSprite);
        } else if (weapon instanceof Sword) {
            imageView.setImage(swordSprite);
        } else if (weapon instanceof Bow) {
            imageView.setImage(bowSprite);
        }
        Rectangle2D viewport = new Rectangle2D(spriteX, spriteY, spriteWidth, spriteHeight);
        imageView.setViewport(viewport);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(100);
        imageView.setLayoutY(250);
        imageGroup = new Group();
        imageGroup.getChildren().add(imageView);

        // hp
        currentHealth = 450;
        maxHealth = 450;
        numHearts = (int) Math.floor(maxHealth / Heart.HEALTH_PER_HEART);
        updatePlayerMaxHp();

        initializeHotbar();
    }

    public int attack() {
        Animation animation = null;
        int currX = spriteX;
        int currY = spriteY;
        double duration = 0;
        if (weapon instanceof Spear) {
            spriteY = spriteY - 258; //go to the attack frames
            duration = 500;
            animation = new SpriteAnimation(imageView, Duration.millis(duration),
                    8, 8, spriteY,  0, spriteWidth, spriteHeight - 2);
            animation.setCycleCount(1);
            animation.play();
        } else if (weapon instanceof Sword) {
            spriteY += 259;
            duration = 500;
            animation = new SpriteAnimation(imageView, Duration.millis(duration), 6, 6, spriteY,
                    0, spriteWidth, spriteHeight);
            animation.setCycleCount(1);
            animation.play();
        } else if (weapon instanceof Bow) {
            spriteY += 508;
            duration = 500;
            animation = new SpriteAnimation(imageView, Duration.millis(duration), 12, 12, spriteY,
                    0, spriteWidth, spriteHeight);
            animation.setCycleCount(1);
            animation.play();
            ((Bow) weapon).fireArrow(direction, positionX, positionY + width / 2, damage * damageWeaponModifier * damageBuffModifier);
        }
        animation.setOnFinished(actionEvent -> {
            spriteX = currX;
            spriteY = currY;
            Rectangle2D viewpoint = new Rectangle2D(spriteX, spriteY, spriteWidth, spriteHeight);
            imageView.setViewport(viewpoint);
        });
        return (int) ((duration / 1000) * 60);
    }

    public void play(Scene scene) {
        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(
            e -> {
                String code = e.getCode().toString();
                if (!input.contains(code)) {
                    input.add(code);
                }
            });

        scene.setOnKeyReleased(
            e -> {
                String code = e.getCode().toString();
                input.remove(code);
            });




        // PLAYER LOGIC
        this.playerLogic = new AnimationTimer() {
            private int invisibilityCd = 0;
            private int damageWindow = 0;

            //For checking if 'I' is pressed and released.
            private boolean containedI = false;

            public void handle(long now) {
                // game logic
                // 60 now's = 1 second!!!
                if (attackCD > 0) {
                    attackCD--;
                }
                if (input.contains("J") && attackCD <= 0) {
                    currSpeed = 0;
                    attackCD = 50;
                    int temp = attack();
                    damageWindow = temp;
                    moveCD = temp;
                } else if (input.size() > 1) {
                    currSpeed = speed * speedWeaponModifier * speedBuffModifier / Math.sqrt(2);
                } else {
                    currSpeed = speed * speedWeaponModifier * speedBuffModifier;
                }
                if (damageWindow > 0) {
                    for (Monster monster : GameLoop.getMonsters()) {
                        if (Controller.getPlayer().intersects(monster) && !(weapon instanceof Bow)) {
                            System.out.println("Player damaged monster for " + damage * damageWeaponModifier * damageBuffModifier);
                            monster.takeDamage(damage * damageWeaponModifier * damageBuffModifier);
                            damageWindow = 0;
                        }
                    }
                    damageWindow--;
                }
                if (damageCooldown > 0 && damageCooldown % 15 == 0) { // got hit
                    invisibilityCd = 5; // set invis frames
                }
                damageCooldown--; // so dmgcd triggers for first frame (60 % 15)

                if (moveCD > 0) {
                    moveCD--;
                } else {
                    if (input.contains("A") && positionX > 10) {
                        imageView.setScaleX(1);
                        moveRelative(-currSpeed, 0);
                        direction = 0;
                    }
                    if (input.contains("D") && positionX + width < (scene.getWidth() - 25)) {
                        imageView.setScaleX(-1);
                        moveRelative(currSpeed, 0);
                        direction = 1;
                    }
                    if (input.contains("W") && positionY > 10) {
                        moveRelative(0, -currSpeed);
                    }
                    if (input.contains("S") && positionY + height < (scene.getHeight() - 55)) {
                        moveRelative(0, currSpeed);
                    }
                }

                if (invisibilityCd > 0) { // Overwrite setScale if invis frames
                    imageView.setScaleX(0);
                } else if (imageView.getScaleX() == 0) { // If player didn't move
                    if (direction == 0) { // same direction
                        imageView.setScaleX(1);
                    } else if (direction == 1) {
                        imageView.setScaleX(-1);
                    }
                }
                invisibilityCd--;


                //Keyboard transitions to screens
                if (input.contains("I")) {
                    containedI = true;
                }
                if (containedI && !input.contains("I")) { //Go to inventory if I was released.
                    Controller.goToInventory();
                    input.remove("I");
                    containedI = false;
                }

            }
        };

        // ITEM LOOP
        this.itemLoop = new AnimationTimer() {
            private ArrayList<Integer> toDelete = new ArrayList<Integer>();
            HBox effectDisplays = Controller.getGameScreen().getEffectDisplays();
            private int itemCD = 0;

            public void handle(long currentNanoTime) {
                Item[] currHotbar = Inventory.getHotbar();

                // some triggers for onscreen hotbar / consumables
                for (int i = 0; i < Inventory.getmaxHotbarSize(); i++) { // max hotbar size of 5
                    Item item = currHotbar[i];
                    if (item != null) {
                        if (itemCD <= 0 && input.contains("DIGIT" + Integer.toString(i + 1))) {
                            if(!(item instanceof HealthPotion) && activeConsumables.size() >= maxActiveConsumables) {
                                //Too many potions active!
                                System.out.println("Too many potions already active!");
                                continue;
                            }
                            item.setActive(true);
                            itemCD = 30;
                            if (item instanceof Consumable) {
                                toDelete.add(i);
                                System.out.println("Trying to delete at index " + i);
                            }
                            potionsUsed++;
                        }
                    }
                    if (item instanceof Consumable && item.isActive()) {
                        activeConsumables.add((Consumable)item);
                    }
                }
                updateHotbar(toDelete, null);
                itemCD--;


                effectDisplays.getChildren().clear();
                for (int i = 0; i < activeConsumables.size(); i++) {
                    //For handling item effects.
                    Consumable item = activeConsumables.get(i);
                    if (item.isActive()) {
                        (item).effect(currentNanoTime);
                    } else {
                        activeConsumables.remove(item);
                    }

                    //for updating the UI with the item+timer
                    //Note: this is pretty inefficient/scuffed, but it works.
                    //It's essentially recreating the display every loop through, because
                    //we have effectDisplays.getChildren().clear() up outside of this for loop.
                    Image icon = item.getImage();
                    ImageView iconView = new ImageView(icon);
                    iconView.setFitWidth(20);
                    iconView.setFitHeight(20);
                    long timer = ((Consumable) item).getDurationTimer() / 60;
                    Text timerText = new Text(10, 10, Long.toString(timer));
                    VBox consumableDisplay = new VBox();
                    consumableDisplay.getChildren().addAll(iconView, timerText);
                    effectDisplays.getChildren().add(consumableDisplay);
                }

            }
        };
    }

    public void moveAbsolute(double x, double y) {
        positionX = x;
        positionY = y;
        imageGroup.relocate(positionX, positionY);
    }

    public void moveRelative(double x, double y) {
        positionX += x;
        positionY += y;
        imageGroup.relocate(positionX, positionY);
    }


    public void updateHotbar(ArrayList<Integer> toDelete, ArrayList<Item> toAdd) {
        boolean update = false;
        Item[] currHotbar = Inventory.getHotbar();
        if (toDelete != null && toDelete.size() > 0) {
            update = true;
            for (int i = toDelete.size() - 1; i >= 0; i--) {
                currHotbar[toDelete.remove(i).intValue()] = null;
                Inventory.setHotbarSize(Inventory.getHotbarSize() - 1);
            }
        }
        if (toAdd != null && toAdd.size() > 0) {

            update = true;
            for (Item item : toAdd) {
                if (Inventory.getHotbarSize() < Inventory.getmaxHotbarSize()) {
                    for (int i = 0; i < Inventory.getmaxHotbarSize(); i++) {
                        if (currHotbar[i] == null) {
                            currHotbar[i] = item;
                            Inventory.setHotbarSize(Inventory.getHotbarSize() + 1);
                            break;
                        }
                    }
                } else {
                    boolean added = Inventory.addToInventory(item);
                }

            }

        }

        if (update) {
            // hotbarBox
            updateHotbarImages();
        }
    }

    public void initializeHotbar() {
        ItemDatabase.resetQuantities();

        this.hotbar = Inventory.getHotbar(); // UPDATE IF WEAPON IS AN ITEM
        this.hotbarBox = new HBox(hotbarPadding);
        for (int i = 0; i < Inventory.getmaxHotbarSize(); i++) {
            Group hotbarSlot = new Group();
            hotbarSlot.getChildren().add(new ImageView("sprites/inventorySlot.png"));
            hotbarBox.getChildren().add(hotbarSlot);

            ImageView imageView = null;
            if (hotbar[i] != null) {
                imageView = new ImageView(hotbar[i].getImage());
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                hotbarSlot.getChildren().add(imageView);
            }

            hotbarBox.setLayoutX(700);
            hotbarBox.setLayoutY(hotbarPadding);
        }
    }


    public void updateHotbarImages() {
        for (int j = 0; j < Inventory.getmaxHotbarSize(); j++) {
            if (hotbar[j] != null) {
                ImageView imageView = new ImageView(hotbar[j].getImage());
                imageView.setFitWidth(hotbar[j].getWidth());
                imageView.setFitHeight(hotbar[j].getHeight());
                Group hotbarSlot = (Group) hotbarBox.getChildren().get(j);
                hotbarSlot.getChildren().clear();
                hotbarSlot.getChildren().add(new ImageView("sprites/inventorySlot.png"));
                hotbarSlot.getChildren().add(imageView);
            } else {
                Group hotbarSlot = (Group) hotbarBox.getChildren().get(j);
                hotbarSlot.getChildren().clear();
                hotbarSlot.getChildren().add(new ImageView("sprites/inventorySlot.png"));
            }
        }
    }



    public void updatePlayerHp() {
        this.playerHpUpdate = new AnimationTimer() {
            private double oldHealth = getCurrentHealth();
            public void handle(long currentNanoTime) {
                double currentHealth = getCurrentHealth();

                if (currentHealth != oldHealth) {

                    if (currentHealth <= 25) { // higher number b/c some glitch
                        Controller.goToDeathScreen();
                    }

                    updateHearts(currentHealth);
                }

                oldHealth = currentHealth;
            }
        };
    }

    public void updateHearts(double currentHealth) {
        int emptyThreshold = (int) Math.floor(currentHealth / Heart.HEALTH_PER_HEART);
        for (int i = 0; i < hearts.size(); i++) {
            if (i >= emptyThreshold) {
                hearts.get(i).setEmpty();
            } else {
                hearts.get(i).setFull();
            }
        }

        ArrayList<ImageView> heartsImages = new ArrayList<ImageView>(hearts.size());
        for (int j = 0; j < hearts.size(); j++) {
            heartsImages.add(hearts.get(j).getImageView());
        }
        heartsBox.getChildren().setAll(heartsImages);
    }

    public void updatePlayerMaxHp() {
        this.hearts = new ArrayList<Heart>(numHearts);
        this.heartsBox = new HBox(heartsPadding);
        for (int i = 0; i < numHearts; i++) {
            Heart heart = new Heart(heartsDimensions, heartsDimensions, true);
            hearts.add(heart);
            heartsBox.getChildren().add(heart.getImageView());
        }
        heartsBox.setLayoutX(heartsPadding);
        heartsBox.setLayoutY(800 - heartsDimensions - heartsPadding);
        if (Controller.getGameScreen() != null) {
            Controller.getGameScreen().getBoard().getChildren().remove(heartsBox);
            Controller.getGameScreen().getBoard().getChildren().add(heartsBox);
        }
    }

    public void swapWeapon(Weapon w) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Weapon Swap");
        a.setHeaderText("Swapping Weapons");
        a.setContentText("Would you like to swap weapons?");
        Optional<ButtonType> response = a.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            setWeapon(w);
        }
    }

    public Group getGroup() {
        return imageGroup;
    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

    public HBox getHeartsBox() {
        return heartsBox;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Touchable other) {
        if (other == null) {
            return false;
        }
        boolean intersected = other.getBoundary().intersects(this.getBoundary());

        return intersected;
    }

    public int getCoins() {
        return coins;
    }
    public synchronized void setCoins(int amount) {
        coins = amount;
        if (Controller.getGameScreen() != null) {
            Controller.getGameScreen().getCoinDisplay().setText("Coins: " + getCoins());
        }
        if (coins > greedyIndex) {
            greedyIndex = coins;
        }
    }
    public void addCoins(int amount) {
        setCoins(coins + amount);
    }
    public void subtractCoins(int amount) {
        setCoins(coins - amount);
    }
    public void setWeapon(Weapon w) {
        weapon = w;
        if (weapon instanceof Spear) {
            imageView.setImage(spearSprite);
        } else if (weapon instanceof Sword) {
            imageView.setImage(swordSprite);
        } else if (weapon instanceof Bow) {
            imageView.setImage(bowSprite);
        }
        damageWeaponModifier = weapon != null ? weapon.getDamage() : 1;
        speedWeaponModifier = weapon != null ? weapon.getSpeed(): 1;

    }
    public Weapon getWeapon() {
        return weapon;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double value) {
        this.speed = value;
    }

    public double getCurrSpeed() {
        return this.currSpeed;
    }

    public double getSpeedBuffModifier() {
        return speedBuffModifier;
    }
    public void setSpeedBuffModifier(double modifier) {
        speedBuffModifier = modifier;
    }

    // DAMAGE || HP
    public void addDamageStat(double value) {
        this.damage += value;
    }

    public void subtractDamageStat(double value) {
        addDamageStat(-value);
    }

    public double getDamageBuffModifier() {
        return damageBuffModifier;
    }
    public void setDamageBuffModifier(double modifier) {
        damageBuffModifier = modifier;
    }

    public double getDamageStat() {
        return this.damage;
    }

    public double getDamageCooldown() {
        return damageCooldown;
    }

    public void setHealth(double value) {
        this.currentHealth = value;
    }
    public void setMaxHealth(double value) {
        this.maxHealth = value;
        numHearts = (int) Math.floor(maxHealth / Heart.HEALTH_PER_HEART);
    }
    public void addHealth(double value) {
        this.currentHealth += value;
    }
    public void takeDamage(double damage) {
        if (damageCooldown <= 0) {
            addHealth(-damage);
            damageCooldown = 60;
        }
    }

    public void addMaximumHealth(double value) {
        this.maxHealth += value;
        this.currentHealth += value;
        this.numHearts += (int) Math.floor(value / Heart.HEALTH_PER_HEART);
        updatePlayerMaxHp();
    }

    public void subtractMaximumHealth(double value) {
        addMaximumHealth(-value);
    }

    public double getMaximumHealth() {
        return this.maxHealth;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }


    public HBox gethotbarBox() {
        return this.hotbarBox;
    }

    public AnimationTimer getPlayerLogicTimer() {
        return playerLogic;
    }

    public AnimationTimer getPlayerHpUpdateTimer() {
        return playerHpUpdate;
    }

    public AnimationTimer getItemLoop() {
        return itemLoop;
    }

    public static int getGreedyIndex() {
        return greedyIndex;
    }
    public static void setGreedyIndex(int amount) {
        greedyIndex = amount;
    }
    public static int getPotionsUsed() {
        return potionsUsed;
    }
    public static void setPotionsUsed(int amount) {
        potionsUsed= amount;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
