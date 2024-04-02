package am24group6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
    private static final String fileEasy = "highscoreEasy.txt";
    private static final String fileHard = "highscoreHard.txt";

    public static void saveHighscore(double score, boolean highScoreLevel){
        try (FileWriter wr = new FileWriter(highScoreLevel ? fileEasy : fileHard)) {
            wr.write(Integer.toString((int) score));            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getHighScore(boolean highScoreLevel){
        int highScore = 0;
        try {
            File file = new File(highScoreLevel ? fileEasy : fileHard);
            if (!file.exists()){
                return 0;
            }

            BufferedReader reader = new BufferedReader(new FileReader(highScoreLevel ? fileEasy : fileHard));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }
}
