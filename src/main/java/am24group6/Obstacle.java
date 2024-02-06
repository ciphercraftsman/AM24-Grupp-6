package am24group6;

import java.awt.Rectangle;

// Use this Alien class as a base for obstacles
public class Obstacle {
    public final int created;
    public final Rectangle bounds;

    public Obstacle(int created, int x, int y) {
        this.created = created;
        this.bounds = new Rectangle(x, y, 100, 200);
    }
}
