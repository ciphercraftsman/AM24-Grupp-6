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
import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A simple panel with game inside.
 * 
 */
public class GameSurface extends JPanel implements KeyListener {
    @Serial
    private static final long serialVersionUID = 6260582674762246325L;
    private static final Logger logger = Logger.getLogger(GameSurface.class.getName());
    private final transient FrameUpdater updater;
    private boolean gameOver;
    private final Rectangle birb;
    private transient BufferedImage birbImageSprite;
    private int birbImageSpriteCount;
    private final int gravity = 3;
    private boolean isJumping = false;
    private boolean firstJump = true;

    public GameSurface(final int width, final int height) {
        // We don't have the graphics ready but will keep for later
        try {
            this.birbImageSprite = ImageIO.read(new File("birb.png"));
            this.birbImageSpriteCount = 0;
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Unable to load image", ex);
        }

        this.gameOver = false;
        this.birb = new Rectangle(width / 2 - 40, height / 2 - 80, 40, 40);

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
            // Code for game over event, customize in later sprint
            //g.setColor(Color.red);
            //g.fillRect(0, 0, d.width, d.height);
            //g.setColor(Color.black);
            //g.setFont(new Font("Arial", Font.BOLD, 48));
            //g.drawString("Game over!", 20, d.width / 2 - 24);
            return;
        }

        // fill the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, d.width, d.height);


        // keep this function for later when we have graphics
        // draw the birb, as a cool image if it did load properly
        if (birbImageSprite != null) {
            int offset = 46 * birbImageSpriteCount;
            g.drawImage(birbImageSprite, birb.x, birb.y, birb.x + birb.width,
                    birb.y + birb.height, offset, 0, offset + 46, 20, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(birb.x, birb.y, birb.width, birb.height);
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

        if (isJumping) {
            birb.translate(0, -50);
            isJumping = false;
        }
        else if (!firstJump) {
            birb.translate(0, gravity);
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this event triggers when we release a key and then
        // we will move the space ship if the game is not over yet

        if (gameOver) {
            return;
        }

        final int minHeight = 10;
        final int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE && birb.y > minHeight) {
            if (firstJump) {
                firstJump = false;
            }
            isJumping = true;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed in this project but must be overridden
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // not needed in this project but must be overridden
    }
}
