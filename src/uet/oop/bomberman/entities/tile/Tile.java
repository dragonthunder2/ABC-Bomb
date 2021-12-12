package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Location;

/**
 * Entity cố định, không di chuyển
 */
public abstract class Tile extends Entity {

    public Tile(int x, int y, Sprite sprite) {
        _x = x;
        _y = y;
        _sprite = sprite;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(Location.tileToPixel(_x), Location.tileToPixel(_y), this);
    }

    @Override
    public void update() {
    }
}
