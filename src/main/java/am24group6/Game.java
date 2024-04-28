package am24group6;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    public static final int FRAME_WIDTH = 384;
    public static final int FRAME_HEIGHT = 640;

    
    // Images
    Image backgroundGif;
    Image dripstoneLayer;
    Image rockLayer;
    Image birbImage;
    Image birbStartImage;
    Image topObstacleImage;
    Image bottomObstacleImage;
    Image obstacleImage;
    
    // Font
    Font superLegendBoy;

    // Sound
    Sound sound = new Sound();
    
    // Birb placement
    final int birbX = FRAME_WIDTH / 8;
    final int birbY = 95;

    // Dripstone layer placement
    int dripstoneLayerX = 0;
    
    // Rock layer placement
    int rockLayerX = 0;
    
    // game logic
    Birb birb;
    int velocityY; // Justerar fågelns upp/ner fart.
    final int gravity = 1;
    
    // Level dependant
    int velocityX; // Flyttar obstacles åt vänster (farten)
    int jump;
    int obstacleDistance = 0;
    int openingSpace;
    
    ArrayList<Obstacle> obstacles;
    
    MenuActionListener actionListener;
    
    // Timers
    Timer gameLoop;
    Timer placeObstacleTimer;
    // Timer för att spelet ska frysa efter att fågeln dör, fortsätter i Game
    // classen.
    Timer pausTimer;
    
    double score;
    double highScore;
    
    boolean gameStarted = false;
    boolean canRestart;
    boolean gameOver;
    boolean canPressKey = true;
    Menu menu;
    GameState gameState;
    MouseInput mouseInput;
    int mouseX;
    int mouseY;
    int level;
    boolean highScoreLevel;

    enum GameState {
        MENU,
        PLAYING
    }

    Game() {

        // load font
        superLegendBoy = FontHandler.loadCustomFont(22f, "/SuperLegendBoy.ttf");

        // load images
        backgroundGif = new ImageIcon(getClass().getResource("/background1.gif")).getImage();
        dripstoneLayer = new ImageIcon(getClass().getResource("/dripstonelayer.png")).getImage();
        rockLayer = new ImageIcon(getClass().getResource("/rocklayer.png")).getImage();
        
        birbStartImage = new ImageIcon(getClass().getResource("/birb_hanging.png")).getImage();
        birbImage = new ImageIcon(getClass().getResource("/birb_flapping.gif")).getImage();
        obstacleImage = new ImageIcon(getClass().getResource("/obstacle.png")).getImage();

        dripstoneLayerX = 0;
        rockLayerX = 0;

        // Menu and adds mouse listener to this JPanel.
        menu = new Menu(this);
        menu.actionListener = new MenuActionListener() {
            public void startGameWithLevel(int selectedLevel) {
                startGame(selectedLevel);
            }

        };

        addMouseListener(menu);
        addKeyListener(menu);
        setFocusable(true);
        gameState = GameState.MENU;

        // birb = new Birb(birbImage, birbX, birbY);
        birb = new Birb(null, birbX, birbY);

        obstacles = new ArrayList<Obstacle>();

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        setFocusable(true); // gör så det är denna klass som tar emot keyevents
        addKeyListener(this);

        // Paus funktionen när fågeln dör.
        pausTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canRestart = true;
                pausTimer.stop();
                startGame(level); // Inte ultimat men gör så att meny-renderingen funkar
                canPressKey = true;
            }
        });

        // Place obstacles timer
        placeObstacleTimer = new Timer(obstacleDistance, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeObstacles();
            }
        });

        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
        System.out.println("GAMELOOP STARTED");
    }

    public void placeObstacles() {
        if (gameStarted) {
            int[] randomObstacleYHeights = { -400, -350, -300, -250, -200, -150 };
            int randomIndex = ThreadLocalRandom.current().nextInt(5);
            int randomObstacleY = randomObstacleYHeights[randomIndex];

            // Top obstacle
            Obstacle topObstacle = new Obstacle(obstacleImage, FRAME_WIDTH);
            topObstacle.y = randomObstacleY;
            obstacles.add(topObstacle);

            // Nedre obstacle
            Obstacle bottomObstacle = new Obstacle(obstacleImage, FRAME_WIDTH);
            bottomObstacle.y = topObstacle.y + bottomObstacle.height + openingSpace;
            obstacles.add(bottomObstacle);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameState == GameState.MENU) {
            // System.out.println("RENDERING MENU");
            menu.render(g, FRAME_WIDTH, FRAME_HEIGHT);
        } else if (gameState == GameState.PLAYING) {
            draw(g);
        }
    }

    public void draw(Graphics g) {
        // background images (dripstone and rock layers need two copies for side scrolling animation)
        g.drawImage(backgroundGif, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
       
        g.drawImage(dripstoneLayer, dripstoneLayerX, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
        g.drawImage(dripstoneLayer, dripstoneLayerX + FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
        
        g.drawImage(rockLayer, rockLayerX, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
        g.drawImage(rockLayer, rockLayerX + FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, null);

        if (gameState == GameState.PLAYING && !gameStarted) {
            // birb hanging before game started
            g.drawImage(birbStartImage, birbX, birbY, 12, 30, null);
        } else {
            // birb
            g.drawImage(birb.img, birb.x, birb.y, birb.width, birb.height, null);
        }

        // obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            g.drawImage(obstacleImage, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        // Skriver ut resultatet.
        g.setColor(Color.white);
        g.setFont(superLegendBoy);

        if (gameOver) {
            if (score > highScore) {
                highScore = score;
                HighScore.saveHighscore(highScore, highScoreLevel);
                playSoundEffect(2);
            }
            g.drawString("Score : " + String.valueOf((int) score), 10, 35); // x & y är kordinater för texten
            g.drawString("Highscore : " + String.valueOf((int) HighScore.getHighScore(highScoreLevel)), 10, 70);
            
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        // birb
        velocityY += gravity;
        birb.y += velocityY;
        birb.y = Math.max(birb.y, 0);

        // dripstone layer
        dripstoneLayerX += velocityX + 2;

        if (dripstoneLayerX <= -FRAME_WIDTH) {
            dripstoneLayerX = 0;
        }

        // rockLayer
        rockLayerX += velocityX;

        if (rockLayerX <= -FRAME_WIDTH) {
            rockLayerX = 0;
        }

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
        if (birb.y > FRAME_HEIGHT) {
            gameOver = true;
        }

        
    }

    public boolean collision(Birb a, Obstacle b) {
        return a.x < b.x + b.width && // a's top left corner doesnt reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesnt reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    public void setStartValues() {
        birb.y = birbY;
        velocityY = 0;
        score = 0;
        gameOver = false;
        canRestart = false;
        gameStarted = false;
        canPressKey = true;
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            move();
        }

        repaint(); // anropar paintComponent()

        // Stoppar spelet när gameOver.
        if (gameOver) {
            playSoundEffect(1);
            placeObstacleTimer.stop();
            gameLoop.stop();
            System.out.println("GAMELOOP STOPPED");
            pausTimer.start(); // startar en paus timer när fågeln kraschar på 0,5sek
            canPressKey = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.PLAYING && canPressKey) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
    
                if (gameOver && canRestart) {
                    startGame(level); // Starta om spelet med samma nivå
                } else {
                    birb.img = birbImage;
                    velocityY = jump;
                    playSoundEffect(0);
                    if (!gameStarted) {
                        gameStarted = true;
                        placeObstacleTimer.start();
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // Återgå till menyn oavsett spelets tillstånd
                gameState = GameState.MENU;
                setStartValues();
                obstacles.clear();
                System.out.println("GOING TO MENU");
                // reset menu values
                repaint(); // Uppdatera gränssnittet för att visa menyn
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // mouseX = e.getX();
        // mouseY = e.getY();
        // System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + ")");

        // if (gameState == GameState.MENU) {
        // if (menu.easyButton.contains(mouseX, mouseY)) {
        // startGame(1); // Anropa startGame från menyns actionListener
        // } else if (menu.hardButton.contains(mouseX, mouseY)) {
        // startGame(2); // Anropa startGame från menyns actionListener
        // }
        // }
    }

    
    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement mouseReleased functionality here
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement mouseEntered functionality here
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement mouseExited functionality here
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        // Implement mouseDragged functionality here
    }
    
    public void startGameWithLevel(int selectedLevel) {
        // Implementera startGameWithLevel-metoden här
        actionListener.startGameWithLevel(selectedLevel);
    }

    private void startGame(int selectedLevel) {
        System.out.println("GAME SCREEN OPENED");
        this.level = selectedLevel;
        gameState = GameState.PLAYING;
        obstacles.clear();
        setStartValues();
    
        // birb = new Birb(birbImage, birbX, birbY);
    
        switch (level) {
            case 1 -> {
                velocityX = -4;
                obstacleDistance = 1600;
                placeObstacleTimer.setDelay(obstacleDistance);
                placeObstacleTimer.restart();
                jump = -9;
                openingSpace = FRAME_HEIGHT / 4;
                highScoreLevel = true;
                highScore = HighScore.getHighScore(highScoreLevel);
            }
            case 2 -> {
                velocityX = -7;
                obstacleDistance = 1100;
                placeObstacleTimer.setDelay(obstacleDistance);
                placeObstacleTimer.restart();
                jump = -10;
                openingSpace = FRAME_HEIGHT / 6;
                highScoreLevel = false;
                highScore = HighScore.getHighScore(highScoreLevel);
            }
        }
    
        gameLoop.start();
        System.out.println("GAMELOOP STARTED");
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    
        if (gameState == GameState.MENU) {
            if (menu.easyButton.contains(mouseX, mouseY)) {
                // Anropa startGame-metoden direkt för att starta spelet med lätt svårighetsgrad
                startGame(1);
                return; // Exit the method after starting the game
            } else if (menu.hardButton.contains(mouseX, mouseY)) {
                // Anropa startGame-metoden direkt för att starta spelet med svår svårighetsgrad
                startGame(2);
            } else if (menu.quitButton.contains(mouseX, mouseY)) {
                // Quit the application
                System.exit(0);
            }
    
        } else if (gameOver && canRestart) {
            // Restart the game if it's over and can be restarted
            setStartValues();
            obstacles.clear();
            gameLoop.start();
            System.out.println("GAMELOOP STARTED");
            placeObstacleTimer.start();
        } else {
            // Trigger jump when mouse is pressed
            velocityY = jump;
        }
    }
}
