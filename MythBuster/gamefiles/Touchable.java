package gamefiles;

import javafx.geometry.Rectangle2D;

public interface Touchable {
    public Rectangle2D getBoundary();
    public boolean intersects(Touchable other);
}
