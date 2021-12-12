package uet.oop.bomberman;

import uet.oop.bomberman.entities.BombSet.Bomb;
import uet.oop.bomberman.entities.BombSet.ExplosionSection;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.GraphicInterface;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLoadLevel;
import uet.oop.bomberman.level.LevelLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class GameComponents implements GraphicInterface {

    protected LevelLoader levelLoader;
    protected Game game;
    protected Keyboard input;
    protected Screen screen;

    public Entity[] _entities;
    public List<Character> _characters = new ArrayList<>();

    private int switchscreen = -1;

    private int points = Game.POINTS;

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setShow(int i) {
        switchscreen = i;
    }

    public int getWidth() {
        return levelLoader.getWidth();
    }

    public GameComponents(Game game, Keyboard input, Screen screen) {
        this.game = game;
        this.input = input;
        this.screen = screen;

        loadLevel(1);
    }

    @Override
    public void update() {
        if (game.isPaused()) return;

        updateEntities();
        updateCharacters();
        updateBombs();

        for (int i = 0; i < _characters.size(); i++) {
            Character a = _characters.get(i);
            if (a.isRemoved()) _characters.remove(i);
        }
    }

    @Override
    public void render(Screen screen) {
        if (game.isPaused()) return;

        int x0 = Screen.xOffset >> 4;
        int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE;
        int y0 = Screen.yOffset >> 4;
        int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE;
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                _entities[x + y * levelLoader.getWidth()].render(screen);
            }
        }
        renderCharacter(screen);
        renderBombs(screen);
    }

    public void nextLevel() {
        Game.setBombRadius(1);
        Game.setBombRate(1);
        Game.setBomberSpeed(1.0);
        loadLevel(levelLoader.getLevel() + 1);
    }

    public void loadLevel(int level) {
        switchscreen = 2;
        game.resetScreenDelay();
        game.pause();
        _characters.clear();
        bombs.clear();
        Game.setBombRadius(1);
        Game.setBombRate(1);
        Game.setBomberSpeed(1.0);

        try {
            levelLoader = new FileLoadLevel(this, level);
            _entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];

            levelLoader.createEntities();
        } catch (Exception e) {
            printStackTrace();
        }
    }

    public void endGame() {
        switchscreen = 1;
        game.resetScreenDelay();
        game.pause();
    }

    public boolean detectNoEnemies() {
        int count = 0;
        for (Character character : _characters) {
            if (!(character instanceof Bomber))
                ++count;
        }
        return count == 0;
    }

    public void drawScreen(Graphics g) {
        switch (switchscreen) {
            case 1:
                Game.State = Game.STATE.GAMEOVER;
                screen.drawEndGame(g, points);
                points = 0;
                break;
            case 2:
                if (levelLoader.getLevel() > 2) {
                    Game.State = Game.STATE.GAMEOVER;
                    screen.drawWinGame(g, points);
                    points = 0;
                } else {
                    screen.drawChangeLevel(g, levelLoader.getLevel());
                }
                break;
        }
    }

    public Entity getEntity(double x, double y, Character m) {

        Entity res;

        res = getFlameSegmentAt((int) x, (int) y);
        if (res != null) return res;

        res = getBombAt(x, y);
        if (res != null) return res;

        res = getCharacterAtExcluding((int) x, (int) y, m);
        if (res != null) return res;

        res = getEntityAt((int) x, (int) y);

        return res;
    }

    public Bomber getBomber() {
        Iterator<Character> itr = _characters.iterator();

        Character cur;
        while (itr.hasNext()) {
            cur = itr.next();

            if (cur instanceof Bomber)
                return (Bomber) cur;
        }
        return null;
    }

    public Character getCharacterAtExcluding(int x, int y, Character a) {
        Iterator<Character> itr = _characters.iterator();

        Character cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (cur == a) {
                continue;
            }

            if (cur.getXTile() == x && cur.getYTile() == y) {
                return cur;
            }

        }

        return null;
    }

    public Entity getEntityAt(double x, double y) {
        return _entities[(int) x + (int) y * levelLoader.getWidth()];
    }

    public void addEntity(int pos, Entity e) {
        _entities[pos] = e;
    }

    public void addCharacter(Character e) {
        _characters.add(e);
    }

    protected void renderCharacter(Screen screen) {

        for (Character character : _characters) character.render(screen);
    }

    protected void updateEntities() {
        for (Entity entity : _entities) {
            entity.update();
        }
    }

    protected void updateCharacters() {
        Iterator<Character> itr = _characters.iterator();

        while (itr.hasNext() && !game.isPaused())
            itr.next().update();
    }

    public Keyboard getInput() {
        return input;
    }

    public Game getGame() {
        return game;
    }

    public int getShow() {
        return switchscreen;
    }


    //----------------------Bomb Setup--------------------------------------------------//

    protected List<Bomb> bombs = new ArrayList<>();

    public List<Bomb> getBombs() {
        return bombs;
    }

    public void addBomb(Bomb e) {
        bombs.add(e);
    }

    protected void updateBombs() {
        if (game.isPaused()) return;

        for (Bomb bomb : bombs) bomb.update();
    }

    public ExplosionSection getFlameSegmentAt(int x, int y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();

            ExplosionSection e = b.ExplosionIn(x, y);
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    public Bomb getBombAt(double x, double y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }
        return null;
    }

    protected void renderBombs(Screen screen) {
        for (Bomb bomb : bombs) bomb.render(screen);
    }

}
