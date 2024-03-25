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

    public Rectangle easyButton = new Rectangle(Game.WIDTH / 2 + 135, 250, 100, 50);
    public Rectangle hardButton = new Rectangle(Game.WIDTH / 2 + 135, 350, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 135, 450, 100, 50);
    // public Rectangle highscoreButton = new Rectangle(Game.WIDTH / 2 + 120, 250,
    // 100, 50);

    public void render(Graphics g, int frameWidth, int frameHeight) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);

        g.drawImage(menuImage, 0, 0, frameWidth, frameHeight, null);

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        // g.drawString("Jumpy Birb", Game.WIDTH / 2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
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

        // switch (selectedOption) {
        // case 0:
        // g.setColor(Color.RED); // Set the color to red for the selected option
        // g.drawString("Easy", easyButton.x + 15, easyButton.y + 33);
        // break;
        // case 1:
        // g.setColor(Color.RED);
        // g.drawString("Hard", hardButton.x + 14, hardButton.y + 33);
        // break;
        // case 2:
        // g.setColor(Color.RED);
        // g.drawString("Quit", quitButton.x + 19, quitButton.y + 33);
        // break;
        // }

    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Game Paused", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (easyButton.contains(e.getX(), e.getY())) {
            System.out.println("Easy Mode");
            // Anropa startGameWithLevel-metoden med lätt svårighetsgrad
            actionListener.startGameWithLevel(1);
        } else if (hardButton.contains(e.getX(), e.getY())) {
            System.out.println("Hard Mode");
            // Anropa startGameWithLevel-metoden med svår svårighetsgrad
            actionListener.startGameWithLevel(2);
        } else if (quitButton.contains(e.getX(), e.getY())) {
            // Avsluta applikationen
            System.exit(0);
        }
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
        System.out.println("aapapap");
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
        } else if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
            // Confirm the selection with space bar or enter key
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

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
