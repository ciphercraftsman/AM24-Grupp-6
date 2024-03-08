package am24group6;

import java.awt.*;

class Obstacle {
    Image img;
    int x;
    int y;
    int width;
    int height;
    boolean passed;

    Obstacle(Image img, int frameWidth) {
        this.img = img;
        this.x = frameWidth;
        this.y = 0;
        this.width = 64;
        this.height = 512;
        this.passed = false;
    }
}