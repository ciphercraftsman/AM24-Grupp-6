package am24group6;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    final int frameWidth = 360;
    final int frameHeight = 640;

    // Images
    Image backgroundImage;
    Image birbImage;
    Image topObstacleImage;
    Image bottomObstacleImage;
    Image obstacleImage;

    // Birb placement & size
    int birbX = frameWidth / 8;
    int birbY = frameHeight / 2;
    int birbWidth = 46;
    int birbHeight = 19;    

    // Obstacles
    int obstacleX = frameWidth;
    int obstacleY = 0;
    int obstacleWidth = 64;
    int obstacleHeight = 512;

    

    // game logic
    Birb birb;
    int velocityX = -4; // Flyttar obstacles åt vänster (farten)
    int velocityY = 0; // Justerar fågelns upp/ner fart.
    int gravity = 5;

    ArrayList<Obstacle> obstacles;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;


    Game(int frameWidth, int frameHeight) {
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setFocusable(true); // gör så det är denna klass som tar emot keyevents
        addKeyListener(this);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("./black_sky.png")).getImage();
        birbImage = new ImageIcon(getClass().getResource("./bat_purple.png")).getImage();
        obstacleImage = new ImageIcon(getClass().getResource("./obstacleImage.png")).getImage();
        // Används inte än då vi bara har en typ av hinder.
        // topObstacleImage = new ImageIcon(getClass().getResource("./birbImage.png")).getImage();
        // bottomObstacleImage = new ImageIcon(getClass().getResource("./birbImage.png")).getImage();


        // Fågel
        birb = new Birb(frameWidth, frameHeight);
        obstacles = new ArrayList<>();

        // Place obstacles timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeObstacles();
            }
        });
        placePipeTimer.start();

        
        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
        
    }

    public void placeObstacles() {
        // Avgör vilken höjd obstacles hamnar på.
        int randomObstacleY = (int) (obstacleY - obstacleHeight/4 - Math.random()*(obstacleHeight/2));
        int openingSpace = frameHeight/4; 

        // Top obstacle
        Obstacle topObstacle = new Obstacle(frameWidth);
        topObstacle.setY(randomObstacleY);
        obstacles.add(topObstacle);

        // Nedre obstacle
        Obstacle bottomObstacle = new Obstacle(frameWidth);
        bottomObstacle.setY(topObstacle.getY() + obstacleHeight + openingSpace);
        obstacles.add(bottomObstacle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        // birb
        g.drawImage(birb.getImage(), birb.getX(), birb.getY(), birb.getWidth(), birb.getHeight(), null);

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            g.drawImage(obstacleImage, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight(), null);
        }

        // Skriver ut resultatet.
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Score : " + String.valueOf((int) score), 10, 35);  // x & y är kordinater för texten
        
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        // birb
        velocityY += gravity;
        birb.setY(birb.getX() + velocityY);
        birb.setY(Math.max(birb.getY(), 0));

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.setX(obstacle.getX() + velocityX);

            if(!obstacle.isPassed() && birb.getX() > obstacle.getX() + obstacle.getWidth()) {
                obstacle.setPassed(true);
                score += 0.5; // 0.5 poäng per hinder för att de räknas som två hinder, ett övre och ett nedre.
            }

            // Om fågeln kraschar med obstacles = gameOver.
            if (collision(birb, obstacle)) {
                gameOver = true;
            }
        }

        // GameOver om fågeln touchar rutans underkant
        if (birb.getY() > frameHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Birb birb, Obstacle obstacle) {
        return birb.getX() < obstacle.getX() + obstacle.getWidth() && // a's top left corner doesnt reach b's top right corner
                birb.getX() + birb.getWidth() > obstacle.getX() && // a's top right corner passes b's top left corner
                birb.getY() < obstacle.getY() + obstacle.getHeight() && // a's top left corner doesnt reach b's bottom left corner
                birb.getY() + birb.getHeight() > obstacle.getY(); // a's bottom left corner passes b's top left corner
            }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // anropar paintComponent()

        // Stoppar spelet när gameOver.
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = 4;
            
            if (gameOver) {
                // startar om spelet och återställer positionerna på fågel och hinder med (Space).
                birb.setY(birbY);
                velocityY = 0;
                obstacles.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipeTimer.start();
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
