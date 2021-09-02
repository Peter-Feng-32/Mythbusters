package gamefiles;

import gamefiles.rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door implements Touchable {

    private Room destination;

    private double positionX;
    private double positionY;
    private Rotation rotation;
    private static double width = 100;
    private static double height = 65;

    private boolean locked;
    private boolean isBossDoor;

    private ImageView imageView;

    public enum Rotation{
        TOP(0),
        RIGHT(90),
        BOTTOM(180),
        LEFT(270);

        private final int value;

        Rotation(final int value) {
            this.value = value;
        }
    }


    private Group doorGroup;
    public Door(double x, double y, Room destination) {
        doorGroup = new Group();
        imageView = new ImageView("sprites/door.png");
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        doorGroup.getChildren().addAll(imageView);
        relocate(x, y);
        this.destination = destination;
        locked = true;
    }

    public Group getGroup() {
        imageView.setRotate(rotation.value);
        if (isBossDoor && isLocked()) {
            imageView.setImage(new Image("sprites/LockedBossDoor.png"));
        } else if (isBossDoor) {
            imageView.setImage(new Image("sprites/OpenBossDoor.png"));
        } else if (isLocked()) {
            imageView.setImage(new Image("sprites/locked_door2.png"));
        } else {
            imageView.setImage(new Image("sprites/door2.png"));
        }
        return doorGroup;
    }
    public void relocate(double x, double y) {
        positionX = x;
        positionY = y;
        doorGroup.relocate(positionX, positionY);
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void unlock() {
        locked = false;
        if(isBossDoor){
            imageView.setImage(new Image("sprites/OpenBossDoor.png"));
        } else {
            imageView.setImage(new Image("sprites/door2.png"));
        }

    }

    public void lock() {
        locked = true;
        if(isBossDoor){
            imageView.setImage(new Image("sprites/LockedBossDoor.png"));
        } else {
            imageView.setImage(new Image("sprites/locked_door2.png"));
        }

    }
    public void setBossDoor() {
        isBossDoor = true;
    }

    public boolean isLocked() {
        return locked;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public static double getWidth() {
        return width;
    }
    public static double getHeight() {
        return height;
    }
    public Room getDestination() {
        return destination;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
