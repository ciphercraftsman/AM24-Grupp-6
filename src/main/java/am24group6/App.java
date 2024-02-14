package am24group6;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * AM24-Grupp6 "Jumpy Birb" game
 * 
 */
public class App {
    public static void main(String[] args) {
        final int frameWidth = 600;
        final int frameHeight = 600;
        
        JFrame main = new JFrame("Jumpy Birb");

        main.setSize(frameWidth, frameHeight);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        GameSurface gameSurface = new GameSurface(frameWidth, frameHeight);
        
        main.add(gameSurface);
        //main.pack();
        main.addKeyListener(gameSurface);
        main.setVisible(true);
    }
}