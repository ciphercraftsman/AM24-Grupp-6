package am24group6;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // Timer för att spelet ska frysa efter att fågeln dör, fortsätter i Game
    // classen.
    Timer pausTimer;
    boolean canRestart = false;

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

    class Birb {
        int x = birbX;
        int y = birbY;
        int width = birbWidth;
        int height = birbHeight;
        Image img;

        Birb(Image img) {
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
    
    // Välj level här!
    int level = 2;

    // game logic
    Birb birb;
    int velocityY = 0; // Justerar fågelns upp/ner fart.
    int gravity = 1;

    // Level dependant
    int velocityX = -4; // Flyttar obstacles åt vänster (farten)
    int jump = -9;
    int obstacleDistance = 1500;
    int openingSpace = frameHeight / 4;

    ArrayList<Obstacle> obstacles;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;
    double highScore = 0;

    Game() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setFocusable(true); // gör så det är denna klass som tar emot keyevents
        addKeyListener(this);

        switch (level) {
            case 1 -> {
                velocityX = -4;
                obstacleDistance = 1600;
                jump = -9;
                openingSpace = frameHeight / 4;
            }

            case 2 -> {
                velocityX = -6;
                obstacleDistance = 1100;
                jump = -10;
                openingSpace = frameHeight / 5;
            }

            case 3 -> {
                velocityX = -4;
                obstacleDistance = 1500;
                jump = -9;
                openingSpace = frameHeight / 5;
            }

            case 4 -> {
                velocityX = -9;
                obstacleDistance = 700;
                jump = -12;
                openingSpace = frameHeight / 4;
            }
        };

        // Paus funktionen nör fågeln dör.
        pausTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canRestart = true;
                pausTimer.stop();
            }
        });

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("./black_sky.png")).getImage();
        birbImage = new ImageIcon(getClass().getResource("./bat_purple.png")).getImage();
        obstacleImage = new ImageIcon(getClass().getResource("./obstacleImage.png")).getImage();
        // Används inte än då vi bara har en typ av hinder.
        // topObstacleImage = new
        // ImageIcon(getClass().getResource("./birbImage.png")).getImage();
        // bottomObstacleImage = new
        // ImageIcon(getClass().getResource("./birbImage.png")).getImage();

        // Fågel
        birb = new Birb(birbImage);
        obstacles = new ArrayList<Obstacle>();

        // Place obstacles timer
        placePipeTimer = new Timer(obstacleDistance, new ActionListener() {
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
        // Avgör vilken höjd obstacles hamnar på (originalsättet)
        // int randomObstacleY = (int) (obstacleY - obstacleHeight / 4 - Math.random() *
        // (obstacleHeight / 2));

        // Nytt sätt att få randomiserade hinder
        int[] randomObstacleYHeights = { -400, -350, -300, -250, -200, -150 };
        int randomIndex = ThreadLocalRandom.current().nextInt(5);
        int randomObstacleY = randomObstacleYHeights[randomIndex];

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

        // birb
        g.drawImage(birb.img, birb.x, birb.y, birb.width, birb.height, null);

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            g.drawImage(obstacleImage, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        // Skriver ut resultatet.
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Score : " + String.valueOf((int) score), 10, 35); // x & y är kordinater för texten
            g.drawString("Highscore : " + String.valueOf((int) highScore), 10, 70);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        // birb
        velocityY += gravity;
        birb.y += velocityY;
        birb.y = Math.max(birb.y, 0);

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.x += velocityX;

            if (!obstacle.passed && birb.x > obstacle.x + obstacle.width) {
                obstacle.passed = true;
                score += 0.5; // 0.5 poäng per hinder för att de räknas som två hinder, ett övre och ett
                              // nedre.
            }

            // Om fågeln kraschar med obstacles = gameOver.
            if (collision(birb, obstacle)) {
                gameOver = true;
            }
        }

        // GameOver om fågeln touchar rutans underkant
        if (birb.y > frameHeight) {
            gameOver = true;
        }

        if (score > highScore) {
            highScore = score;
        }
    }

    public boolean collision(Birb a, Obstacle b) {
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
            pausTimer.start(); // startar en paus timer när fågeln kraschar på 0,5sek
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = jump;

            if (gameOver && canRestart) {
                // startar om spelet och återställer positionerna på fågel och hinder med
                // (Space).
                birb.y = birbY;
                velocityY = 0;
                obstacles.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipeTimer.start();
                canRestart = false;
            } else {
                velocityY = jump;
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