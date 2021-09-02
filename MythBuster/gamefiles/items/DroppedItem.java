package gamefiles.items;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Droppable;
import gamefiles.Touchable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;

public class DroppedItem implements Droppable {
    private Item item;
    private double width;
    private double height;
    private double positionX;
    private double positionY;
    private Rectangle2D boundary;
    private ImageView imageView;
    private Image image;
    private Group dropGroup;
    private boolean dropped;
    private int cooldown = 60;

    protected static volatile Queue<AnimationTimer> atQueue = new LinkedList<>();

    public DroppedItem(Item item) {
        this.item = item;
        this.width = 50;
        this.height = 50;
        this.positionX = 0;
        this.positionY = 0;
        this.boundary = new Rectangle2D(positionX, positionY, width, height);
        this.dropGroup = new Group();
        this.dropped = false;
    }

    public Item getItem() {
        return item;
    }

    public void setPositionX(double value) {
        this.positionX = value;
        updateBoundary();
    }

    public void setPositionY(double value) {
        this.positionX = value;
        updateBoundary();
    }

    public Rectangle2D getBoundary() {
        return boundary;
    }

    public void updateBoundary(double positionX, double positionY,
                                double width, double height) {
        this.boundary = new Rectangle2D(positionX, positionY, width, height);
    }

    public void updateBoundary() {
        this.boundary = new Rectangle2D(this.positionX, this.positionY, 
                                        this.width, this.height);
    }

    public void setWidth(double value) {
        this.width = value;
        updateBoundary();
    }

    public void setHeight(double value) {
        this.height = value;
        updateBoundary();
    }

    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public void drop(double x, double y, boolean randomize) {
        if (randomize) {
            randomizeDropLocation(x, y);
        } else {
            positionX = x;
            positionY = y;
        }

        updateBoundary();
        
        this.imageView = item.getImageView();
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        this.image = imageView.getImage();

        dropGroup.getChildren().add(this.imageView);
        dropGroup.relocate(positionX, positionY);
        
        Controller.getCurrentRoom().addDrop(this);
        GameLoop.getDrops().add(this);
    }

    protected void randomizeDropLocation(double x, double y) {
        double rFactorX = Math.random() * 100;
        positionX = x + rFactorX - width / 2;
        if (positionX < 0) {
            positionX = 0;
        } else if (positionX + width >= Controller.getW()) {
            positionX = Controller.getW() - width;
        }
        double rFactorY = Math.random() * 100;
        positionY = y + rFactorY - height / 2;
        if (positionY < 0) {
            positionY = 0;
        } else if (positionY + height >= Controller.getH()) {
            positionY = Controller.getH() - height;
        }
    } 

    public void pickup() {
        if (!dropped) {
            dropped = true;
            if (!atQueue.isEmpty()) {
                AnimationTimer e = atQueue.remove();
                e.stop();
            }
            displayReward("You picked up a " + item.getName());
            ArrayList<Item> toAdd = new ArrayList<Item>();
            toAdd.add(item);
            Controller.getPlayer().updateHotbar(null, toAdd);

            Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().remove(this.getGroup());
                Controller.getCurrentRoom().removeDrop(this);
                dropGroup.getChildren().removeAll(imageView);
            });
        }
    }

    public void setDropped(boolean dropStatus) {
        this.dropped = dropStatus;
    }

    public boolean getDropped() {
        return dropped;
    }

    public void update() {
        if (cooldown <= 0 && this.intersects(Controller.getPlayer())) {
            pickup();
        } else {
            cooldown--;
        }
        // space for some poggers animation if wanted
    }

    public void displayReward(String text) {
        Label display = new Label(text);
        display.setPrefWidth(Controller.getW());
        display.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER; -fx-font-family: Papyrus");
        display.setLayoutY(200);
        display.setId("dropNotificationDisplay");
        Controller.getGameScreen().getBoard().getChildren().add(display);


        new AnimationTimer() {
            private int timer = 40;

            @Override
            public void start() {
                super.start();
                atQueue.add(this);
            }

            @Override
            public void stop() {
                super.stop();
                Controller.getGameScreen().getBoard().getChildren().remove(display);
                atQueue.remove(this);
            }

            @Override
            public void handle(long currentNanoTime) {
                timer--;
                if (timer <= 0) {
                    this.stop();
                }
            }
        }.start();
    }

    public Group getGroup() {
        return dropGroup;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
