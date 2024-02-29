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

        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setResizable(false);
        
        GamePanel gamePanel = new GamePanel();
        main.add(gamePanel);
        
        main.pack();
        
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        gamePanel.requestFocus();

        gamePanel.startGameThread();
    }
}