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
    Image obstacleImage;

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
        boolean passed = false; // kollar om fågeln har tagit sig förbi hindret och används för att hålla koll
        // på poängen.

        Obstacle(Image img) {
            this.img = img;
        }
    }

    // game logic
    Bird bird;
    int velocityX = -4; // Flyttar obstacles åt vänster (farten)
    int velocityY = 0; // Justerar fågelns upp/ner fart.
    int gravity = 1;

    ArrayList<Obstacle> obstacles;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;


    Game() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setFocusable(true); // gör så det är denna klass som tar emot keyevents
        addKeyListener(this);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("./background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();
        obstacleImage = new ImageIcon(getClass().getResource("./obstacleImage.png")).getImage();
        // Används inte än då vi bara har en typ av hinder.
        // topObstacleImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();
        // bottomObstacleImage = new ImageIcon(getClass().getResource("./birdImage.png")).getImage();


        // Fågel
        bird = new Bird(birdImage);
        obstacles = new ArrayList<Obstacle>();

        // Place obstacles timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipeTimer.start();

        
        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
        
    }

    public void placePipes() {
        // Avgör vilken höjd obstacles hamnar på.
        int randomObstacleY = (int) (obstacleY - obstacleHeight/4 - Math.random()*(obstacleHeight/2));
        int openingSpace = frameHeight/4; 

        // Top obstacle
        Obstacle topObstacle = new Obstacle(obstacleImage);
        topObstacle.y = randomObstacleY;
        obstacles.add(topObstacle);

        // Nedre obstacle
        Obstacle bottomObstacle = new Obstacle(obstacleImage);
        bottomObstacle.y = topObstacle.y + obstacleHeight + openingSpace;
        obstacles.add(bottomObstacle);
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

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            g.drawImage(obstacleImage, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
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
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.x += velocityX;

            if(!obstacle.passed && bird.x > obstacle.x + obstacle.width) {
                obstacle.passed = true;
                score += 0.5; // 0.5 poäng per hinder för att de räknas som två hinder, ett övre och ett nedre.
            }

            // Om fågeln kraschar med obstacles = gameOver.
            if (collision(bird, obstacle)) {
                gameOver = true;
            }
        }

        // GameOver om fågeln touchar rutans underkant
        if (bird.y > frameHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Obstacle b) {
        return a.x < b.x + b.width && // a's top left corner doesnt reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesnt reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
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
            velocityY = -9;
            
            if (gameOver) {
                // startar om spelet och återställer positionerna på fågel och hinder med (Space).
                bird.y = birdY;
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
