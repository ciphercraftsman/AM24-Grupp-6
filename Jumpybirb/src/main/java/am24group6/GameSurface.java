package am24group6;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A simple panel with a space invaders "game" in it. This is just to
 * demonstrate the bare minimum of stuff than can be done drawing on a panel.
 * This is by no means good code, but rather a short demonstration on
 * some things one can do to make a very simple Swing based game.
 * 
 * If you really want to make a good game there are several toolkits for
 * game making out there which are much more suitable for this.
 * 
 */
public class GameSurface extends JPanel implements KeyListener {
    private static final long serialVersionUID = 6260582674762246325L;
    private static Logger logger = Logger.getLogger(GameSurface.class.getName());

    private transient FrameUpdater updater;
    private boolean gameOver;
    private Rectangle spaceShip;
    private transient BufferedImage shipImageSprite;
    private int shipImageSpriteCount;

    public GameSurface(final int width) {
        try {
            this.shipImageSprite = ImageIO.read(new File("ship.png"));
            this.shipImageSpriteCount = 0;
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Unable to load image", ex);
        }

        this.gameOver = false;
        this.spaceShip = new Rectangle(20, width / 2 - 15, 46, 20);

        this.updater = new FrameUpdater(this, 60);
        this.updater.setDaemon(true); // it should not keep the app running
        this.updater.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawSurface(g2d);
    }

    /**
     * Call this method when the graphics needs to be repainted on the graphics
     * surface.
     * 
     * @param g the graphics to paint on
     */
    private void drawSurface(Graphics2D g) {
        final Dimension d = this.getSize();

        if (gameOver) {
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Game over!", 20, d.width / 2 - 24);
            return;
        }

        // fill the background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, d.width, d.height);

   

        // draw the space ship, as a cool image if it did load properly
        if (shipImageSprite != null) {
            int offset = 46 * shipImageSpriteCount;
            g.drawImage(shipImageSprite, spaceShip.x, spaceShip.y, spaceShip.x + spaceShip.width,
                    spaceShip.y + spaceShip.height, offset, 0, offset + 46, 20, null);
        } else {
            g.setColor(Color.black);
            g.fillRect(spaceShip.x, spaceShip.y, spaceShip.width, spaceShip.height);
        }
    }

    public void update(int time) {
        if (gameOver) {
            updater.interrupt();
            return;
        }

        final Dimension d = getSize();
        if (d.height <= 0 || d.width <= 0) {
            // if the panel has not been placed properly in the frame yet
            // just return without updating any state
            return;
        }

   
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this event triggers when we release a key and then
        // we will move the space ship if the game is not over yet

        if (gameOver) {
            return;
        }

        final int minHeight = 10;
        final int maxHeight = this.getSize().height - spaceShip.height - 10;
        final int kc = e.getKeyCode();

        if (kc == KeyEvent.VK_UP && spaceShip.y > minHeight) {
            spaceShip.translate(0, -10);
        } else if (kc == KeyEvent.VK_DOWN && spaceShip.y < maxHeight) {
            spaceShip.translate(0, 10);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // do nothing
    }
}
