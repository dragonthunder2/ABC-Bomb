package uet.oop.bomberman.entities.BombSet;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemies.Enemy;
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
                    _sprite = Sprite.explosion_vertical2;
                } else {
                    _sprite = Sprite.explosion_vertical_top_last2;
                }
            break;
            case 1:
                if(!lastOf) {
                    _sprite = Sprite.explosion_horizontal2;
                } else {
                    _sprite = Sprite.explosion_horizontal_right_last2;
                }
                break;
            case 2:
                if(!lastOf) {
                    _sprite = Sprite.explosion_vertical2;
                } else {
                    _sprite = Sprite.explosion_vertical_down_last2;
                }
                break;
            case 3:
                if(!lastOf) {
                    _sprite = Sprite.explosion_horizontal2;
                } else {
                    _sprite = Sprite.explosion_horizontal_left_last2;
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
        if(e instanceof Bomber) ((Bomber) e).kill();
        if(e instanceof Enemy) ((Enemy) e).kill();
        return true;
    }

    @Override
    public void update() {}
}
