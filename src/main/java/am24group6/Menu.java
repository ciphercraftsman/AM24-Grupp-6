package am24group6;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Anpassat gränssnitt för menyhändelser
interface MenuActionListener {
    void startGameWithLevel(int selectedLevel);
    // Lägg till eventuella andra metoder för menyhändelser här
}

public class Menu implements MouseListener, KeyListener {
    MenuActionListener actionListener;
    int selectedOption = 0;

    // Images
    Image logo = new ImageIcon(getClass().getResource("/logo.png")).getImage();
    Image menuImage = new ImageIcon(getClass().getResource("/menu.png")).getImage();
    Image flappyBirb = new ImageIcon(getClass().getResource("/birb_flapping.gif")).getImage();

    // Font superLegendBoy;
    Font superLegendBoy = FontHandler.loadCustomFont(22f, "/SuperLegendBoy.ttf");

    public Rectangle easyButton = new Rectangle(Game.WIDTH / 2 + 135, 250, 100, 50);
    public Rectangle hardButton = new Rectangle(Game.WIDTH / 2 + 135, 350, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 135, 450, 100, 50);
    // public Rectangle highscoreButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
    private Game game;

    int logoX = 0;
    int logoY = Game.FRAME_HEIGHT;

    int birbX = Game.WIDTH / 2 + 135;
    int birbY = 266;

    // Actionlistener for animation
    ActionListener animationListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update the Y coordinate of the logo to make it move upwards
            logoY -= 5; // Adjust the speed of the animation as needed

            // Repaint the game window to reflect the updated position of the logo
            game.repaint();

            // Check if the logo has reached its final position
            if (logoY <= 0) {
                logoTimer.stop(); // Stop the animation timer
                System.out.println("LOGO TIMER STOPPED");
            }
        }
    };

    // Timers for animating the logo and birb
    Timer logoTimer;

    public Menu(Game game) {
        this.game = game;
        logoTimer = new Timer(20, animationListener);
        logoTimer.start();
        System.out.println("LOGO TIMER STARTED");
    }

    public void render(Graphics g, int frameWidth, int frameHeight) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        g.drawImage(logo, logoX, logoY, null);

        g.setFont(superLegendBoy);

        // System.out.println(logoY);

        if (logoY == 0) {
            g.drawImage(menuImage, 0, 0, null);
            g.drawImage(flappyBirb, birbX -30, birbY, null);

            // Highlight the currently selected option
            if (selectedOption == 0) {
                g.setColor(Color.RED);
                birbY = 266;
                // System.out.println("SELECTED OPTION: " + selectedOption + " BIRBY: " + birbY);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString("Easy", birbX + 15, 283);
            
            if (selectedOption == 1) {
                g.setColor(Color.RED);
                birbY = 366;
                // System.out.println("SELECTED OPTION: " + selectedOption + " BIRBY: " + birbY);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString("Hard", birbX + 14, 383);
            
            if (selectedOption == 2) {
                g.setColor(Color.RED);
                birbY = 466;
                // System.out.println("SELECTED OPTION: " + selectedOption + " BIRBY: " + birbY);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString("Quit", birbX + 19, 483);
            
            g.setColor(Color.WHITE);
            g.drawString("Press Enter to select", 23, 600);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Navigate through options with up and down arrow keys
        if (key == KeyEvent.VK_UP) {
            System.out.println("Moving up");
            selectedOption--;
            if (selectedOption < 0) {
                selectedOption = 2; // Wrap around to the last option
            }
        } else if (key == KeyEvent.VK_DOWN) {
            System.out.println("Moving down");
            selectedOption++;
            if (selectedOption > 2) {
                selectedOption = 0; // Wrap around to the first option
            }
        } else if (key == KeyEvent.VK_ENTER) {
            // Confirm the selection with enter key
            switch (selectedOption) {
                case 0:
                    System.out.println("Easy Mode");
                    actionListener.startGameWithLevel(1);
                    break;
                case 1:
                    System.out.println("Hard Mode");
                    actionListener.startGameWithLevel(2);
                    break;
                case 2:
                System.exit(0);
                break;
            }
        }

        game.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    // Code for mouse that doesn't work 100%
    @Override
    public void mouseClicked(MouseEvent e) {
        // if (easyButton.contains(e.getX(), e.getY())) {
        //     System.out.println("Easy Mode");
        //     // Anropa startGameWithLevel-metoden med lätt svårighetsgrad
        //     actionListener.startGameWithLevel(1);
        // } else if (hardButton.contains(e.getX(), e.getY())) {
        //     System.out.println("Hard Mode");
        //     // Anropa startGameWithLevel-metoden med svår svårighetsgrad
        //     actionListener.startGameWithLevel(2);
        // } else if (quitButton.contains(e.getX(), e.getY())) {
        //     // Avsluta applikationen
        //     System.exit(0);
        // }
    }

    // Övriga metoder från MouseListener som måste implementeras
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
