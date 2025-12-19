package game.story;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

public class StoryAssets {

    // Example: "bg_school.png" or "portraits/hikari_happy.png"
    public static Image loadImage(String path) {
        // Assumes files are on the classpath in /resources/
        try (InputStream in = StoryAssets.class.getResourceAsStream("/" + path)) {
            if (in == null) {
                System.err.println("[StoryAssets] Image not found: " + path);
                return null;
            }
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
