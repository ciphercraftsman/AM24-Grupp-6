package am24group6;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

// Anpassat gränssnitt för menyhändelser
interface MenuActionListener {
    void startGameWithLevel(int selectedLevel);
    // Lägg till eventuella andra metoder för menyhändelser här
}

public class Menu implements MouseListener, KeyListener {
    MenuActionListener actionListener;
    int selectedOption = 0;

    Image menuImage = new ImageIcon(getClass().getResource("/menu.png")).getImage();

    // Testa att animera introt:
    Image logo = new ImageIcon(getClass().getResource("/logo.png")).getImage();
    Image blueRock = new ImageIcon(getClass().getResource("/blue_rock.png")).getImage();
    Image flappyBirb = new ImageIcon(getClass().getResource("/birb_flapping.gif")).getImage();

    // Font superLegendBoy;
    Font superLegendBoy = FontHandler.loadCustomFont(22f, "/SuperLegendBoy.ttf");

    public Rectangle easyButton = new Rectangle(Game.WIDTH / 2 + 135, 250, 100, 50);
    public Rectangle hardButton = new Rectangle(Game.WIDTH / 2 + 135, 350, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 135, 450, 100, 50);
    // public Rectangle highscoreButton = new Rectangle(Game.WIDTH / 2 + 120, 250,
    // 100, 50);
    private Game game;

    public Menu(Game game) {
        this.game = game;
    }

    public void render(Graphics g, int frameWidth, int frameHeight) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        g.drawImage(menuImage, 0, 0, frameWidth, frameHeight, null);
        g.setFont(superLegendBoy);

        // Highlight the currently selected option
        if (selectedOption == 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Easy", easyButton.x + 15, easyButton.y + 33);
        g2d.draw(easyButton);

        if (selectedOption == 1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Hard", hardButton.x + 14, hardButton.y + 33);
        g2d.draw(hardButton);

        if (selectedOption == 2) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Quit", quitButton.x + 19, quitButton.y + 33);
        g2d.draw(quitButton);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Game Paused", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
    }

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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
