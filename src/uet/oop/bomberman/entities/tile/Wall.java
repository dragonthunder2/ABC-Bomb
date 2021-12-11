package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.BombSet.Explosion;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Tile {

    private int _animate = 0;
    protected boolean _destroyed = false;
    protected int _timeToDisapear = 20;
    protected Sprite _belowSprite = Sprite.grass;

    public Wall(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (_destroyed) {
            int MAX_ANIMATE = 7500;
            if (_animate < MAX_ANIMATE) _animate++;
            else _animate = 0;
            if (_timeToDisapear > 0)
                _timeToDisapear--;
            else
                remove();
        }
    }

    public void destroy() {
        _destroyed = true;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Explosion) destroy();
        return false;
    }

    public void addBelowSprite(Sprite sprite) {
        _belowSprite = sprite;
    }

    protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
        int calc = _animate % 30;

        if (calc < 10) {
            return normal;
        }

        if (calc < 20) {
            return x1;
        }
        return x2;
    }

}
