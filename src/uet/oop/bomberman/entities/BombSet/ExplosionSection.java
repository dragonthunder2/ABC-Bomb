package uet.oop.bomberman.entities.BombSet;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionSection extends Entity {
    protected boolean lastOf;

    public ExplosionSection(int x, int y, int direction, boolean lastOf) {
        this._x = x;
        this._y = y;
        this.lastOf = lastOf;
        switch (direction) {
            case 0:
                if (!lastOf) {
                    _sprite = Sprite.explosion_vertical;
                } else {
                    _sprite = Sprite.explosion_vertical_top_last;
                }
            break;
            case 1:
                if(!lastOf) {
                    _sprite = Sprite.explosion_horizontal;
                } else {
                    _sprite = Sprite.explosion_horizontal_right_last;
                }
                break;
            case 2:
                if(!lastOf) {
                    _sprite = Sprite.explosion_vertical;
                } else {
                    _sprite = Sprite.explosion_vertical_down_last;
                }
                break;
            case 3:
                if(!lastOf) {
                    _sprite = Sprite.explosion_horizontal;
                } else {
                    _sprite = Sprite.explosion_horizontal_left_last;
                }
                break;
        }
    }

    @Override
    public void render(Screen screen) {
        int x1 = (int)_x << 4;
        int y2 = (int)_y << 4;
        screen.renderEntity(x1, y2 , this);
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }

    @Override
    public void update() {}
}
