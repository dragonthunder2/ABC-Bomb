package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BufferedImageLoader {

    public BufferedImage loadImage(String Path) throws IOException {

        return ImageIO.read(Objects.requireNonNull(getClass().getResource(Path)));
    }
}
