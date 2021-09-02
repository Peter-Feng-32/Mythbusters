package gamefiles.rooms;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Door;
import gamefiles.DropMethods;
import gamefiles.Droppable;
import gamefiles.Inventory;
import gamefiles.Door.Rotation;
import gamefiles.characters.*;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.Weapon;
import gamefiles.weapons.WeaponDatabase;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import views.GameScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {
    private Door[] doors = new Door[4];
    private int row;
    private int column;
    private int width;
    private int height;
    private Text roomInfo;

    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<Droppable> drops = new ArrayList<>();

    private Group roomGroup;

    public Room(int width, int height, int row, int column) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
        roomInfo = new Text(410, 10, toString());
        roomInfo.setStyle("-fx-font-size: 30;");

        if (this instanceof BasicRoom) {
            //Add at least 1 monster
            Monster defaultMonster = new Soldier();
            defaultMonster.moveAbsolute(Math.random() * width, Math.random() * height);
            monsters.add(defaultMonster);

            //Randomly add more monsters
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnSoldier();
            }
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnMage();
            }
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnTrapMonster();
            }
        }
        if (this instanceof BossRoom) {
            lockDoors();
            Monster boss = new Boss();
            boss.moveAbsolute(width / 2, height / 2);
            GameLoop.getMonsters().add(boss);
            monsters.add(boss);
        }
    }

    public void giveBonusRewards() {
        ArrayList<Item> toAdd = new ArrayList<>();
        int newCoins = 0;
        int weaponKey = (int)(3 * Math.random());
        Weapon w = WeaponDatabase.getWeapon(weaponKey);
        if (!checkWeapon(w)) {
            boolean added = Inventory.addToInventory(w);
        } else {
            newCoins += (int) (50 + Math.random() * 50);
        }
        for (int i = 0; i < 2 + (int)(3 * Math.random()); i++) {
            int itemKey = (int)(3 * Math.random());
            toAdd.add(ItemDatabase.getItem(itemKey));
        }
        newCoins += (int) (50 + Math.random() * 50);
        DropMethods.dropCoins(newCoins, 25, Controller.getW() / 2, Controller.getH() / 2);
        displayReward("You picked up " + newCoins + " coins, and a huge amount of treasure!\nCheck your inventory!");
        Controller.getPlayer().updateHotbar(null, toAdd);

    }

    public boolean checkWeapon(Weapon w) {
        if (w.equals(Controller.getPlayer().getWeapon())) {
            return true;
        }
        List<Item> inventory = Inventory.getInventory();
        for (Item i: inventory) {
            if ((i instanceof Weapon)) {
                Weapon w2 = (Weapon) (i);
                if (w.equals(w2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Door[] getDoors() {
        return doors;
    }
    public Door getLeftDoor() {
        return doors[0];
    }
    public Door getTopDoor() {
        return doors[1];
    }
    public Door getRightDoor() {
        return doors[2];
    }
    public Door getBottomDoor() {
        return doors[3];
    }
    public void setLeftDoor(Room destination) {
        doors[0] = new Door(-15, height / 2 - Door.getWidth() / 2, destination);
        doors[0].setRotation(Rotation.LEFT);
    }
    public void setTopDoor(Room destination) {
        doors[1] = new Door(width / 2 - Door.getWidth() / 2, 0, destination);
        doors[1].setRotation(Rotation.TOP);
    }
    public void setRightDoor(Room destination) {
        doors[2] = new Door(width - Door.getHeight() - 15, height / 2 - Door.getWidth() / 2, destination);
        doors[2].setRotation(Rotation.RIGHT);
    }
    public void setBottomDoor(Room destination) {
        doors[3] = new Door(width / 2 - Door.getWidth() / 2, height - Door.getHeight(), destination);
        doors[3].setRotation(Rotation.BOTTOM);
    }

    public void unlockDoors() {
        for (int i = 0; i < 4; i++) {
            if (doors[i] != null) {
                doors[i].unlock();
            }
        }
    }

    public void lockDoors() {
        for (int i = 0; i < 4; i++) {
            if (doors[i] != null) {
                doors[i].lock();
            }
        }
    }

    public Group getRoomGroup() {
        roomGroup = new Group();
        if (doors[0] != null) {
            roomGroup.getChildren().add(doors[0].getGroup());
        }
        if (doors[1] != null) {
            roomGroup.getChildren().add(doors[1].getGroup());
        }
        if (doors[2] != null) {
            roomGroup.getChildren().add(doors[2].getGroup());
        }
        if (doors[3] != null) {
            roomGroup.getChildren().add(doors[3].getGroup());
        }
        for (Monster monster : monsters) {
            roomGroup.getChildren().add(monster.getGroup());
        }
        for (Droppable drop : drops) {
            roomGroup.getChildren().add(drop.getGroup());
        }
        return roomGroup;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Droppable> getDrops() {
        return drops;
    }

    public Text getRoomInfo() {

        return roomInfo;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    public Soldier spawnSoldier() {
        Soldier soldier = new Soldier();
        soldier.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(soldier);
        GameLoop.getMonsters().add(soldier);
        return soldier;
    }
    public Mage spawnMage() {
        Mage mage = new Mage();
        mage.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(mage);
        GameLoop.getMonsters().add(mage);
        return mage;
    }
    public TrapMonster spawnTrapMonster() {
        TrapMonster trapMonster = new TrapMonster();
        trapMonster.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(trapMonster);
        GameLoop.getMonsters().add(trapMonster);
        return trapMonster;
    }

    public void displayReward(String text) {
        Label display = new Label(text);
        display.setPrefWidth(Controller.getW());
        display.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER; -fx-font-family: Papyrus");
        display.setLayoutY(250);
        display.setId("dropNotificationDisplay");
        Controller.getGameScreen().getBoard().getChildren().add(display);

        new AnimationTimer() {
            private int timer = 180;
            @Override
            public void handle(long currentNanoTime) {
                timer--;
                if (timer <= 0) {
                    Controller.getGameScreen().getBoard().getChildren().remove(display);
                    this.stop();
                }
            }
        }.start();
    }

    // abstract methods for generating monsters and chests will be here.

    public void addDrop(Droppable drop) {
        drops.add(drop);
        Controller.getGameScreen().updateBoardDrop(drop);
    }

    public void removeDrop(Droppable drop) {
        drops.remove(drop);
    }

}
