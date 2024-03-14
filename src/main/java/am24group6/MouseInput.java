package am24group6;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private int mouseX;
    private int mouseY;
    private boolean mousePressed;

    public MouseInput() {
        mouseX = 0;
        mouseY = 0;
        mousePressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }
}