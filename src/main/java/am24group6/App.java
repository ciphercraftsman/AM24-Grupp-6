package am24group6;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * AM24-Grupp6 "Jumpy Birb" game
 * 
 */
public class App {
    public static void main(String[] args) {
        JFrame main = new JFrame("Jumpy Birb");

        final int width = 600;
        final int height = 600;

        GameSurface gameSurface = new GameSurface(width, height);

        main.setSize(width, height);
        main.setResizable(false);
        main.add(gameSurface);
        main.addKeyListener(gameSurface);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
    }
}