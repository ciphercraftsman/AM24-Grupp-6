package am24group6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
    private static final String FILE_EASY = "highscoreEasy.txt";
    private static final String FILE_HARD = "highscoreHard.txt";

    public static void saveHighscore(double score, boolean highScoreLevel) {
        try (FileWriter wr = new FileWriter(highScoreLevel ? FILE_EASY : FILE_HARD)) {
            wr.write(Integer.toString((int) score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getHighScore(boolean highScoreLevel) {
        int highScore = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreLevel ? FILE_EASY : FILE_HARD))) {
            File file = new File(highScoreLevel ? FILE_EASY : FILE_HARD);
            if (!file.exists()) {
                return 0;
            }
            highScore = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }
}