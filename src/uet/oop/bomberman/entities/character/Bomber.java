package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.BombSet.Bomb;
import uet.oop.bomberman.entities.BombSet.Explosion;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemies.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;

import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.graphics.Location;

public class Bomber extends Character {

    protected Keyboard input;

    protected int bombDelay = 0;

    private final List<Bomb> bombs;

    public Bomber(int x, int y, GameComponents gameComponents) {
        super(x, y, gameComponents);
        input = this.gameComponents.getInput();
        _sprite = Sprite.player_right;
        bombs = this.gameComponents.getBombs();
    }

    @Override
    public void update() {
        bombsClear();
        if (!live) {
            afterKill();
            return;
        }

        if (bombDelay < -7500) bombDelay = 0;
        else bombDelay--;

        animate();

        calculateMove();

        placeBombConfirm();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (live)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(gameComponents, this);
        Screen.setOffset(xScroll, 0);
    }

    @Override
    public void kill() {
        if (!live) return;
        live = false;
        Game.audioPlay("fail.wav", false);
    }

    @Override
    protected void afterKill() {
        if (timeAfter > 0) --timeAfter;
        else {
            gameComponents.endGame();
        }
    }

    @Override
    protected void calculateMove() {

        int xa = 0, ya = 0;
        if (input.up) ya--;
        if (input.down) ya++;
        if (input.left) xa--;
        if (input.right) xa++;

        if (xa != 0 || ya != 0) {
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            moving = true;
        } else {
            moving = false;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) {
            double xt = ((_x + x) + c % 2 * 9) / Game.TILES_SIZE;
            double yt = ((_y + y) + c / 2 * 10 - 13) / Game.TILES_SIZE;

            Entity a = gameComponents.getEntity(xt, yt, this);

            if (!a.collide(this))
                return false;
        }

        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (xa > 0) direction = 1;
        if (xa < 0) direction = 3;
        if (ya > 0) direction = 2;
        if (ya < 0) direction = 0;

        if (canMove(0, ya)) {
            _y += ya;
        }

        if (canMove(xa, 0)) {
            _x += xa;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Explosion) {
            this.kill();
            return false;
        }
        if (e instanceof Enemy) {
            this.kill();
            return true;
        }
        if (e instanceof LayeredEntity) return (e.collide(this));
        return true;
    }

    //sprite
    private void chooseSprite() {
        switch (direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }

    private void placeBombConfirm() {
        if (input.space && Game.getBombRate() > 0 && bombDelay < 0) {

            int xt = Location.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Location.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize());

            plantBomb(xt, yt);
            Game.addBombRate(-1);

            bombDelay = 30;
        }
    }

    private void bombsClear() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    protected void plantBomb(int x, int y) {
        Game.audioPlay("bombset.wav", false);
        Bomb b = new Bomb(x, y, gameComponents);
        gameComponents.addBomb(b);
    }

}
