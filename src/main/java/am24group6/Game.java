package am24group6;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // Images
    Image backgroundImage;
    Image birdImage;
    Image topObstacleImage;
    Image bottomObstacleImage;

    // Bird placement & size
    int birdX = frameWidth / 8;
    int birdY = frameHeight / 2;
    int birdWidth = 34;
    int birdHeight = 34;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Obstacles
    int obstacleX = frameWidth;
    int obstacleY = 0;
    int obstacleWidth = 64;
    int obstacleHeight = 512;

    class Obstacle {
        int x = obstacleX;
        int y = obstacleY;
        int width = obstacleWidth;
        int height = obstacleHeight;
        Image img;
        boolean passed = false;

        Obstacle(Image img) {
            this.img = img;
        }
    }

    // game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Obstacle> obstacles;

    Timer gameLoop;
    Timer placePipeTimer;


    Game() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setFocusable(true); // gör så det är denna klass som tar emot keyevents
        addKeyListener(this);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("./backgroundImage.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();
        topObstacleImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();
        bottomObstacleImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();

        bird = new Bird(birdImage);
        obstacles = new ArrayList<Obstacle>();

        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void placePipes() {
        // int randomPipeY = 
        int spaceBetweenObstacles = frameHeight / 4;

        Obstacle topObstacle = new Obstacle(topObstacleImage);
        topObstacle.y = 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move() {
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // anropar paintComponent()
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
