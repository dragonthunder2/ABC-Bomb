package uet.oop.bomberman;

import sun.applet.Main;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.BufferedImageLoader;
import uet.oop.bomberman.ui.Frame;
import uet.oop.bomberman.ui.Menu;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.input.MouseInput;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class Game extends Canvas {

    public static final int TILES_SIZE = 16,
            WIDTH = TILES_SIZE * (31 / 2),
            HEIGHT = 13 * TILES_SIZE;

    public static int SCALE = 5;

    public static final String TITLE = "Bomberman-123";

    private static final int BOMBRATE = 1;
    private static final int BOMBRADIUS = 1;
    private static final double BOMBERSPEED = 1.0;//toc do bomber

    public static final int POINTS = 0;
    private String highScore = "";

    protected static int SCREENDELAY = 3;

    protected static int bombRate = BOMBRATE;
    protected static int bombRadius = BOMBRADIUS;
    protected static double bomberSpeed = BOMBERSPEED;


    protected int _screenDelay = SCREENDELAY;

    private final Keyboard _input;
    public static boolean _paused = true;

    public static GameComponents _gameComponents;
    public static Screen screen;
    private final Frame _frame;

    private final Menu menu;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage background = null;
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public enum STATE {
        MENU,
        GAME,
        GAMEOVER,
        HELP
    }

    public static STATE State = STATE.MENU;

    public void checkScore() {
        if (_gameComponents.getPoints() > Integer.parseInt(highScore)) {
            highScore = "" + _gameComponents.getPoints();
            File scoreFile = new File("highscore.txt");
            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                writer.write(this.highScore);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getHighScore() {
        FileReader readFile;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.txt");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        } catch (Exception e) {
            return "0";
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void audioPlay(String music, boolean loop) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream input = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("/sound/" + music));
            clip.open(input);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.loop(0);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static double getBomberSpeed() {
        return bomberSpeed;
    }

    public static void addBombRadius(int i) {
        bombRadius += i;
    }

    public static void addBomberSpeed(double i) {
        bomberSpeed += i;
    }

    public static int getBombRadius() {
        return bombRadius;
    }

    public static void setBomberSpeed(double bomberSpeed) {
        Game.bomberSpeed = bomberSpeed;
    }

    public static int getBombRate() {
        return bombRate;
    }

    public static void setBombRadius(int bombRadius) {
        Game.bombRadius = bombRadius;
    }

    public static void setBombRate(int bombRate) {
        Game.bombRate = bombRate;
    }

    public static void addBombRate(int i) {
        bombRate += i;
    }

    public void resetScreenDelay() {
        _screenDelay = SCREENDELAY;
    }

    public static GameComponents getBoard() {
        return _gameComponents;
    }

    public boolean isPaused() {
        return _paused;
    }

    public void pause() {
        _paused = true;
    }

    public Game(Frame frame) {
        _frame = frame;
        _frame.setTitle(TITLE);

        screen = new Screen(WIDTH, HEIGHT);
        _input = new Keyboard();

        _gameComponents = new GameComponents(this, _input, screen);
        menu = new Menu();
        addKeyListener(_input);
        addMouseListener(new MouseInput());
    }

    private void renderMenu() {
        try {
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
            screen.clear();

            Graphics g = bs.getDrawGraphics();
            BufferedImageLoader loader = new BufferedImageLoader();
            background = loader.loadImage("/MENU.png");

            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(background, 0, 0, null);

            if (State == STATE.MENU) {
                menu.render(g);
            }

            g.dispose();
            bs.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void renderHelp() {
        try {
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
            screen.clear();

            Graphics g = bs.getDrawGraphics();
            BufferedImageLoader loader = new BufferedImageLoader();
            background = loader.loadImage("/help.png");

            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(background, 0, 0, null);

            g.dispose();
            bs.show();
        } catch (IOException e) {
            printStackTrace();
        }
    }

    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        if (State == STATE.GAME) {

            _gameComponents.render(screen);
        }

        System.arraycopy(screen._pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        if (State == STATE.MENU) {
            menu.render(g);
        }

        highScore = getHighScore();

        checkScore();

        Font font0 = new Font("Arial", Font.BOLD, 30);
        g.setFont(font0);
        g.setColor(Color.yellow);
        g.drawString("BEST SCORE: " + highScore, 20, 60);

        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("SCORE: " + _gameComponents.getPoints(), 20, 30);

        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        Graphics g = bs.getDrawGraphics();
        _gameComponents.setShow(g);
        g.dispose();
        bs.show();
    }

    private void update() {
        _input.update();
        _gameComponents.update();
    }

    public void start() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        requestFocus();
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            if (State == STATE.MENU) {
                renderMenu();
            } else if (State == STATE.HELP) {
                renderHelp();
            } else if (State == STATE.GAME) {
                if (_paused) {
                    if (_screenDelay <= 0) {
                        _gameComponents.setShow(-1);
                        _paused = false;
                    }
                    renderScreen();
                } else {
                    renderGame();
                }

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    _frame.setTitle(TITLE);

                    if (_gameComponents.getShow() == 2)
                        --_screenDelay;
                }
            }
        }
    }


}
