package gamefiles;

import gamefiles.items.Item;
import gamefiles.items.DroppedItem;
import gamefiles.items.DroppedCoin;
import gamefiles.items.ItemDatabase;
import gamefiles.items.Coin;

import java.util.Arrays;

public class DropMethods {
    public static void dropCoins(int newCoins, int num, double positionX, double positionY) {
        int coinsToSplit = newCoins - num;

        if (coinsToSplit <= 0) { // if total coins > number of physical coins dropped
            for (int i = 0; i < newCoins; i++) {
                Item coin = ItemDatabase.getItem(-1);
                coin.addQuantity(1);
                DroppedItem droppedItem = new DroppedCoin(coin);
                droppedItem.drop(positionX, positionY, true);
            }
        } else {
            int[] nums = new int[num]; // reserve one coin for "remainder"
            for (int i = 0; i < nums.length - 1; i++) {
                nums[i] = (int) (Math.random() * coinsToSplit + 1); // determine breaks
            }
            nums[num - 1] = coinsToSplit;

            Arrays.sort(nums);
            
            int lower = 0; // inclusive upper bound: coins = range(lower, upper]
            for (int i = 0; i < nums.length; i++) { // add coins
                Item coin = ItemDatabase.getItem(-1);
                coin.addQuantity(nums[i] - lower + 1);
                ((Coin) coin).updateImageQuantity();
                lower = nums[i];
                DroppedItem droppedItem = new DroppedCoin(coin);
                droppedItem.drop(positionX, positionY, true);
            }
        }
    }

    public static void dropCoinsNotRandom(int newCoins, int num, double positionX, double positionY) {
        int coinsToSplit = newCoins - num;

        if (coinsToSplit <= 0) { // if total coins > number of physical coins dropped
            for (int i = 0; i < newCoins; i++) {
                Item coin = ItemDatabase.getItem(-1);
                coin.addQuantity(1);
                DroppedItem droppedItem = new DroppedCoin(coin);
                droppedItem.drop(positionX, positionY, false);
            }
        } else {
            int[] nums = new int[num]; // reserve one coin for "remainder"
            for (int i = 0; i < nums.length - 1; i++) {
                nums[i] = (int) (Math.random() * coinsToSplit + 1); // determine breaks
            }
            nums[num - 1] = coinsToSplit;

            Arrays.sort(nums);
            
            int lower = 0; // inclusive upper bound: coins = range(lower, upper]
            for (int i = 0; i < nums.length; i++) { // add coins
                Item coin = ItemDatabase.getItem(-1);
                coin.addQuantity(nums[i] - lower + 1);
                ((Coin) coin).updateImageQuantity();
                lower = nums[i];
                DroppedItem droppedItem = new DroppedCoin(coin);
                droppedItem.drop(positionX, positionY, false);
            }
        }
    }
}
