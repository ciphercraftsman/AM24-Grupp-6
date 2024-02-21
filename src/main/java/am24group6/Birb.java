package am24group6;

import java.awt.*;

import javax.swing.*;

class Birb {

    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;

    public Birb(int frameWidth, int frameHeight) {
        x = frameWidth / 8;
        y = frameHeight / 2;
        width = 46;
        height = 19;
        image = new ImageIcon(getClass().getResource("./bat_purple.png")).getImage();
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

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}