package uet.oop.bomberman.entities.BombSet;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemies.Enemy;
import uet.oop.bomberman.graphics.Screen;

public class Explosion extends Entity {
    protected Board board;
    protected int direction;
    protected int xBegin, yBegin;
    protected ExplosionSection[] explosionSections = new ExplosionSection[0];

    private int radius;

    public Explosion(int x, int y, int direction, int radius, Board board) {
        this.xBegin = x;
        this.yBegin = y;
        this._x = x;
        this._y = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;
        createExplosionSections();
    }

    private void createExplosionSections() {
        explosionSections = new ExplosionSection[calculateClearDistance()];

        boolean last;

        int x = (int) _x;
        int y = (int) _y;
        for (int i = 0; i < explosionSections.length; i++) {
            last = i == explosionSections.length - 1;

            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }
            explosionSections[i] = new ExplosionSection(x, y, direction, last);
        }
    }

    private int calculateClearDistance() {
        int _radius = 0;
        int x = (int) _x;
        int y = (int) _y;
        while (_radius < radius) {
            if (direction == 0) y--;
            if (direction == 1) x++;
            if (direction == 2) y++;
            if (direction == 3) x--;

            Entity a = board.getEntity(x, y, null);

            if (a instanceof Bomb) ++_radius;

            if (!a.collide(this))
                break;

            ++_radius;
        }
        return _radius;
    }

    public ExplosionSection explosionSectionIn(int x, int y) {
        for (ExplosionSection explosionSection : explosionSections) {
            if (explosionSection.getX() == x && explosionSection.getY() == y)
                return explosionSection;
        }
        return null;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
        for (ExplosionSection explosionSection : explosionSections) {
            explosionSection.render(screen);
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) ((Bomber) e).kill();
        if (e instanceof Enemy) ((Enemy) e).kill();
        return true;
    }
}
