package uet.oop.bomberman.graphics;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

import java.awt.*;
import java.util.Arrays;

public class Screen {
    protected int _width, _height;
    public int[] _pixels;
    public static int xOffset = 0, yOffset = 0;

    private final int _transparent = 0xffff00ff;

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public int getRealWidth() {
        return _width * Game.SCALE;
    }

    public int getRealHeight() {
        return _height * Game.SCALE;
    }

    public Screen(int width, int height) {
        _width = width;
        _height = height;

        _pixels = new int[width * height];

    }

    public void clear() {
        Arrays.fill(_pixels, 0);
    }

    public static void setOffset(int xO, int yO) {
        xOffset = xO;
        yOffset = yO;
    }

    public void renderEntity(int xp, int yp, Entity entity) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;
                if (xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height)
                    break;
                if (xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != _transparent) _pixels[xa + ya * _width] = color;
            }
        }
    }

    public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;
                if (xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height)
                    break;
                if (xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != _transparent)
                    _pixels[xa + ya * _width] = color;
                else
                    _pixels[xa + ya * _width] = below.getPixel(x + y * below.getSize());
            }
        }
    }

    public static int calculateXOffset(GameComponents gameComponents, Bomber bomber) {
        if (bomber == null) return 0;
        int temp = xOffset;

        double BomberX = bomber.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = gameComponents.getWidth() / 4;
        int lastBreakpoint = gameComponents.getWidth() - firstBreakpoint;

        if (BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
            temp = (int) bomber.getX() - (Game.WIDTH / 2);
        }
        return temp;
    }


    public void stageChange(Graphics g, int level) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("STAGE " + level, 400, 550);
    }

    public void gameOver(Graphics g, int points) {
        g.setColor(Color.red);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Rectangle playButton = new Rectangle(500, 632, 200, 50);
        Graphics2D g2d = (Graphics2D) g;
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(Color.white);
        g.drawString("TRY AGAIN", playButton.x + 20, playButton.y + 35);
        g2d.draw(playButton);

        Rectangle menuButton = new Rectangle(500, 700, 200, 50);
        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.setColor(Color.white);
        g.drawString("MAIN MENU", menuButton.x + 20, menuButton.y + 35);
        g2d.draw(menuButton);

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("GAME OVER", 320, 550);

        font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.yellow);
        g.drawString("SCORE: " + points, 490, 610);
    }

    public void gameWin(Graphics g, int points) {
        g.setColor(Color.yellow);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Rectangle playButton = new Rectangle(500, 632, 200, 50);
        Graphics2D g2d = (Graphics2D) g;
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(Color.white);
        g.drawString("PLAY AGAIN", playButton.x + 20, playButton.y + 35);
        g2d.draw(playButton);

        Rectangle menuButton = new Rectangle(500, 700, 200, 50);
        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.setColor(Color.white);
        g.drawString("MAIN MENU", menuButton.x + 20, menuButton.y + 35);
        g2d.draw(menuButton);

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("YOU WON!!!", 320, 550);


        font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.yellow);
        g.drawString("SCORE: " + points, 490, 610);
    }

}
