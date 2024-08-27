package proj;

import geometry.Mesh;
import geometry.Triangle;
import geometry.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;

public class util {

    public static Vector2 screenDims = new Vector2();


    public static BufferedImage loadTexture(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
