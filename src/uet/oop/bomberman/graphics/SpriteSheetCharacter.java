package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheetCharacter {
    private String _path;
    public final int SIZE;
    public int[] _pixels;

    public static SpriteSheetCharacter bomberman = new SpriteSheetCharacter("/textures/Bombermantiles.png", 256);

    public SpriteSheetCharacter(String path, int size) {
        _path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            URL a = SpriteSheet.class.getResource(_path);
            BufferedImage image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            _pixels = new int[w * h];
            _pixels = image.getRGB(0, 0, w, h, null, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
