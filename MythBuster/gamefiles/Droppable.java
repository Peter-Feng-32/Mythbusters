package gamefiles;

import javafx.scene.Group;

public interface Droppable extends Touchable {
    public void drop(double positionX, double positionY, boolean randomize);
    public void pickup();
    public void update();
    public Group getGroup();
}
