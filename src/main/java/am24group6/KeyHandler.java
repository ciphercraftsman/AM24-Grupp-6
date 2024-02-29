package am24group6;

import java.awt.event.*;

public class KeyHandler implements KeyListener {
    
    boolean spacePressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}
