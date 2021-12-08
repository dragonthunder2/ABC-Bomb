package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {
    private BufferedImage image;

    public BufferedImage loadImage(String Path) throws IOException {

        image = ImageIO.read(getClass().getResource(Path));
        return image;
    }
}
