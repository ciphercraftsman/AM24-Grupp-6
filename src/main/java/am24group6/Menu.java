package am24group6;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
    public Menu(int frameWidth, int frameHeight) {

    }

    public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle highscoreButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);

    public void render(Graphics g, int frameWidth, int frameHeight) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.BLUE);
        g.drawString("Jumpy Birb", Game.WIDTH / 2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 19, playButton.y + 30);
        g2d.draw(playButton);
        g.drawString("Score", highscoreButton.x + 14, highscoreButton.y + 30);
        g2d.draw(highscoreButton);
        g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
        g2d.draw(quitButton);

    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Game Paused", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
    }
}
