package am24group6;

import java.awt.*;

import javax.swing.*;

class Obstacle {
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean passed; // kollar om fågeln har tagit sig förbi hindret och används för att hålla koll
    // på poängen.

    public Obstacle(int frameWidth) {
        image = new ImageIcon(getClass().getResource("./obstacleImage.png")).getImage();
        x = frameWidth;
        y = 0;
        width = 64;
        height = 512;
        passed = false;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    

    
}