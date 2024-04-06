package am24group6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
    public static final String FILE_EASY = "highscoreEasy.txt";
    public static final String FILE_HARD = "highscoreHard.txt";

    public static void saveHighscore(String playerName, double score, boolean highScoreLevel) {
        try (FileWriter wr = new FileWriter(highScoreLevel ? FILE_EASY : FILE_HARD)) {
            wr.write(playerName + " " + Integer.toString((int) score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static int getHighScore(boolean highScoreLevel) {
        int highScore = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreLevel ? FILE_EASY : FILE_HARD))) {
            File file = new File(highScoreLevel ? FILE_EASY : FILE_HARD);
            if (!file.exists() || file.length() == 0) { // Kontrollera om filen inte finns eller är tom
                return 0; // Återvänd 0 om det inte finns något innehåll
            }
            String line = reader.readLine();
            if (line != null) {
                // Dela upp raden i delar separerade av ett mellanslag
                String[] parts = line.split(" ");
                // Konvertera och returnera bara det andra elementet (poängen)
                highScore = Integer.parseInt(parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(); // Kan läggas till för att undersöka omvandlingsfel eller felaktig filstruktur
        }
        return highScore;
    }
    
    
}