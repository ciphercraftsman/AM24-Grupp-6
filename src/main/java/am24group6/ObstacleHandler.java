package am24group6;

import java.awt.*;
import java.util.*;

// Den här klassen ska hantera flera och randomiserade hinder
// Men just nu ligger den funktionen i GamePanel :(

public class ObstacleHandler {
    
    private static final int TOP_OF_SCREEN = 0;

    ArrayList<Obstacle> obstacles = new ArrayList<>();

    public void placeObstacles(int screenWidth, int screenHeight) {
        
        Obstacle topObstacle = new Obstacle(screenWidth);
        
        // Avgör vilken höjd obstacles hamnar på.
        int randomObstacleY = (int) (TOP_OF_SCREEN - topObstacle.getHeight() / 4.0 - Math.random() * (topObstacle.getHeight() / 2.0));
        int openingSpace = screenHeight / 4; 

        // Top obstacle
        topObstacle.y = randomObstacleY;
        obstacles.add(topObstacle);

        // Nedre obstacle
        Obstacle bottomObstacle = new Obstacle(screenWidth);
        bottomObstacle.y = topObstacle.y + bottomObstacle.getHeight() + openingSpace;
        obstacles.add(bottomObstacle);
    }

    public void update() {

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.setX(obstacle.getX() + obstacle.getSpeed());
        }

    }

    public void draw(Graphics2D g2) {

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            g2.drawImage(obstacle.getObstacleImage(), obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }
    }
}
