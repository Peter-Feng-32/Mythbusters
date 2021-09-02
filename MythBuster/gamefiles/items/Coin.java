package gamefiles.items;

public class Coin extends Item {

    public Coin(int id, String name, String description, int quantity) {
        super(id, name, description, quantity);
        updateImageView("sprites/itemAssets/Coin.png");
    }

    public void updateImageQuantity() {
        if (getQuantity() >= 4) {
            updateImageView("sprites/itemAssets/Gem.png");
        } else {
            updateImageView("sprites/itemAssets/Coin.png");
        }
    }
}
