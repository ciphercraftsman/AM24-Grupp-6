package am24group6;

import java.awt.*;
import java.io.*;

public class FontHandler {
    private static Font customFont;

    public static Font loadCustomFont(float size, String fontUrl) {
        InputStream fontStream = FontHandler.class.getResourceAsStream(fontUrl);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.getMessage();
        } finally {
            try {
                if (fontStream != null) {
                    fontStream.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return customFont;
    }
}
