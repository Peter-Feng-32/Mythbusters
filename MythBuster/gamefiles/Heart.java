package gamefiles;

import javafx.scene.image.ImageView;

public class Heart {
    public static final double HEALTH_PER_HEART = 50;

    private double width;
    private double height;
    private boolean full;
    protected ImageView imageView;

    public Heart(double width, double height, boolean full) {
        this.width = width;
        this.height = height;
        this.full = full;

        updateImageView();
    }

    private void updateImageView() {
        String spritePath = "";
        if (this.full) {
            spritePath = "sprites/hpAssets/fullHeart.png";
        } else {
            spritePath = "sprites/hpAssets/emptyHeart.png";
        }

        imageView = new ImageView(spritePath);
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
    }

    public void setFull() {
        if (!this.full) {
            this.full = true;
            updateImageView();
        }
    }

    public void setEmpty() {
        if (this.full) {
            this.full = false;
            updateImageView();
        }
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public boolean isFull() {
        return full;
    }
}
