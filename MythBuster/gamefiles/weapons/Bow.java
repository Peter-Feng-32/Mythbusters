package gamefiles.weapons;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Touchable;
import gamefiles.characters.Monster;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import views.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class Bow extends Weapon {
    private ArrayList<Arrow> arrows = new ArrayList<>();
    private Group arrowGroup = new Group();
    private AnimationTimer arrowTimer;

    public Bow(int id, String name, String description, double speed, double damage) {

        super(id, name, description, speed, damage);
        updateImageView("sprites/itemAssets/BowAndArrow.png");

    }

    public AnimationTimer arrowTimer() {
        arrowTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Iterator<Arrow> iterator = arrows.iterator(); iterator.hasNext();) {
                    Arrow arrow = iterator.next();
                    arrow.erase();
                    for (Monster monster : GameLoop.getMonsters()) {
                        if (arrow.intersects(monster)) {
                            monster.takeDamage(arrow.damage);
                            iterator.remove();
                            break;
                        }
                    }
                    if (arrow.direction == 1) {
                        arrow.x += 6;
                    } else {
                        arrow.x -= 6;
                    }
                    if (arrow.x >= Controller.getW() || arrow.x <= 0) {
                        iterator.remove();
                    }
                    if (arrows.contains(arrow)) {
                        arrow.update();
                        arrow.draw();
                    }
                }
            }
        };
        return arrowTimer;
    }
    public AnimationTimer getArrowTimer() {
        return arrowTimer;
    }

    public void fireArrow(int direction, double startX, double startY, double damage) {
        arrows.add(new Arrow(direction, startX, startY, damage));
    }

    public Group getArrowGroup() {
        return arrowGroup;
    }

    private class Arrow implements Touchable {
        private final Image arrow = new Image("sprites/arrow.png");
        private ImageView imageView;
        private double x;
        private double y;
        private double damage;
        private final int width = 40;
        private final int height = 12;
        protected int direction;

        private GameScreen screen = Controller.getGameScreen();

        public Arrow(int direction, double x, double y, double damage)  {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.damage = damage;
            imageView = new ImageView(arrow);
            Rectangle2D viewpoint = new Rectangle2D(310, 110, 140, 30);
            imageView.setViewport(viewpoint);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            if (direction == 1) {
                imageView.setScaleX(-1);
            }
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            arrowGroup.getChildren().add(imageView);
        }

        @Override
        public Rectangle2D getBoundary() {
            return new Rectangle2D(x, y, width, height);
        }

        @Override
        public boolean intersects(Touchable other) {
            return this.getBoundary().intersects(other.getBoundary());
        }

        public void erase() {
            screen.getBoard().getChildren().removeAll(arrowGroup);
            arrowGroup.getChildren().remove(imageView);
            screen.getBoard().getChildren().addAll(arrowGroup);
        }

        public void draw() {
            screen.getBoard().getChildren().removeAll(arrowGroup);
            arrowGroup.getChildren().add(imageView);
            screen.getBoard().getChildren().addAll(arrowGroup);
        }

        public void update() {
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
        }

    }


}
