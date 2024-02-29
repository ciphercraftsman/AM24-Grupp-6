package am24group6;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class Obstacle {
    int x;
    int y;
    int speed;
    BufferedImage obstacleImage;
    int width;
    int height;
    GamePanel gamePanel;
    KeyHandler keyHandler;
    boolean passed;

    public Obstacle(GamePanel gamePanel, KeyHandler keyHandler, int screenWidth) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        width = 64;
        height = 512;
        setDefaultValues(screenWidth);
        getImage();
    }

    public Obstacle(int screenWidth) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        width = 64;
        height = 512;
        setDefaultValues(screenWidth);
        getImage();
    }

    public void setDefaultValues(int screenWidth) {
        x = screenWidth;
        y = 640 - height;
        speed = 4;
        passed = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public BufferedImage getObstacleImage() {
        return obstacleImage;
    }

    public void getImage() {
        try {
            obstacleImage = ImageIO.read(getClass().getResourceAsStream("obstacleImage.png"));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void update() {
        x -= speed;

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(obstacleImage, x, y, width, height, null);
    }

}
