package am24group6;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

// Anpassat gränssnitt för menyhändelser
interface MenuActionListener {
    void startGameWithLevel(int selectedLevel);
    // Lägg till eventuella andra metoder för menyhändelser här
}

public class Menu extends JPanel implements MouseListener {
    MenuActionListener actionListener;

    public Menu(int frameWidth, int frameHeight, MenuActionListener actionListener) {
        this.actionListener = actionListener;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        addMouseListener(this);
    }

    Image menuImage = new ImageIcon(getClass().getResource("/menu.png")).getImage();

    public Rectangle easyButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle hardButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
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
        g.drawString("Easy", easyButton.x + 19, easyButton.y + 30);
        g2d.draw(easyButton);
        g.drawString("Hard", hardButton.x + 14, hardButton.y + 30);
        g2d.draw(hardButton);
        g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
        g2d.draw(quitButton);

    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Game Paused", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (easyButton.contains(e.getX(), e.getY())) {
            // Anropa startGameWithLevel-metoden med lätt svårighetsgrad
            actionListener.startGameWithLevel(1);
        } else if (hardButton.contains(e.getX(), e.getY())) {
            // Anropa startGameWithLevel-metoden med svår svårighetsgrad
            actionListener.startGameWithLevel(2);
        } else if (quitButton.contains(e.getX(), e.getY())) {
            // Avsluta applikationen
            System.exit(0);
        }
    }

    // Övriga metoder från MouseListener som måste implementeras
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
