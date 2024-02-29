package am24group6;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen settings
    public static final int SCREEN_WIDTH = 360;
    public static final int SCREEN_HEIGHT = 640;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Birb birb = new Birb(this, keyHandler);
    ObstacleHandler obstacleHandler = new ObstacleHandler();
    
    Obstacle obstacle = new Obstacle(this, keyHandler, SCREEN_WIDTH);

    private final int FPS = 60; 

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
        double drawInterval = 1000_000_000 / (double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        
        while (gameThread != null) {
            
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }

    }

    public void update() {

        birb.update();
        obstacle.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        obstacle.draw(g2);
        birb.draw(g2);
        g2.dispose();
    }

}
