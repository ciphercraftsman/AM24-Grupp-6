package am24group6;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class Birb {

    // Testar att ha dem package private till att börja med
    int x;
    int y;
    int jump;
    int gravity;
    BufferedImage birbNeutral;
    BufferedImage birbUp;
    BufferedImage birbDown;
    int width;
    int height;
    int spriteCounter = 0;
    int spriteNumber = 1;
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Birb(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        width = 46;
        height = 50;
        setDefaultValues();
        getBirbImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        jump = 16;
        gravity = 8;
    }

    public void getBirbImage() {
        try {
            birbNeutral = ImageIO.read(getClass().getResourceAsStream("bat1.png"));
            birbUp = ImageIO.read(getClass().getResourceAsStream("bat2.png"));
            birbDown = ImageIO.read(getClass().getResourceAsStream("bat3.png"));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void update() {
        y += gravity;

        if (keyHandler.spacePressed) {
            y -= jump;
        }

        spriteCounter++;
        if (spriteCounter > 5) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            }
            else if (spriteNumber == 2) {
                spriteNumber = 3;
            }
            else if (spriteNumber == 3) {
                spriteNumber = 4;
            }  
            else if (spriteNumber == 4) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        if (spriteNumber == 1) {
            image = birbNeutral;
        }
        else if (spriteNumber == 2) {
            image = birbUp;
        }
        else if (spriteNumber == 3) {
            image = birbNeutral;
        }
        else if (spriteNumber == 4) {
            image = birbDown;
        }

        g2.drawImage(image, x, y, width, height, null);
    }

}
