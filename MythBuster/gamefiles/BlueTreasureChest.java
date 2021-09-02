package gamefiles;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.items.DroppedItem;
import gamefiles.items.DroppedWeapon;
import gamefiles.weapons.Weapon;
import gamefiles.weapons.WeaponDatabase;
import javafx.animation.AnimationTimer;

public class BlueTreasureChest extends TreasureChest {
    public BlueTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestBlue.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        int random = (int) (Math.random() * 2);
        switch (random) {
        case 0:
            addPotion();
            break;
        case 1:
            addWeapon();
            break;
        default:
            addWeapon();
            break;
        }
        opened = true;
    }

    private void addPotion() {
        int amountToAdd = (int) (Math.random() * 3 + 2);
        ArrayList<Item> itemsToAdd = new ArrayList<Item>();
        for (int i = 0; i < amountToAdd; i++) {
            addRandomPotion(itemsToAdd);
        }
        for (int i = 0; i < itemsToAdd.size(); i++) {
            DroppedItem droppedItem = new DroppedItem(itemsToAdd.get(i));
            droppedItem.drop(positionX, positionY, true);
        }
        if (!atQueue.isEmpty()) {
            AnimationTimer e = atQueue.remove();
            e.stop();
        }
        displayReward("You have gained " + Integer.toString(amountToAdd) + " potions!");
    }

    private void addRandomPotion(ArrayList<Item> itemsToAdd) {
        int random = (int) (Math.random() * 3);
        switch (random) {
        case 0:
            Item healthPotion = ItemDatabase.getItem(0);
            healthPotion.addQuantity(1);
            itemsToAdd.add(healthPotion);
            break;
        case 1:
            Item ragePotion = ItemDatabase.getItem(1);
            ragePotion.addQuantity(1);
            itemsToAdd.add(ragePotion);
            break;
        case 2:
            Item hastePotion = ItemDatabase.getItem(2);
            hastePotion.addQuantity(1);
            itemsToAdd.add(hastePotion);
            break;
        default:
            Item hastePotion2 = ItemDatabase.getItem(2);
            hastePotion2.addQuantity(1);
            itemsToAdd.add(hastePotion2);
            break;
        }
    }

    private void addWeapon() {
        Weapon weapon = WeaponDatabase.getWeapon((int) (Math.random() * 3));
        if (!hasWeapon(weapon)) {
            DroppedItem droppedItem = new DroppedWeapon(weapon);
            droppedItem.drop(positionX, positionY, true);
        } else {
            addPotion();
        }
    }

    public boolean hasWeapon(Weapon w) {
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
}
