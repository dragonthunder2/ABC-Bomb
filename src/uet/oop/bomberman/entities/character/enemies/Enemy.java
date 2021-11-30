package uet.oop.bomberman.entities.character.enemies;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.BombSet.Explosion;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemies.AI.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {
    protected int _points;
    protected double _speed;
    protected AI _ai;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double _steps;

    protected int _finalAnimation = 30;
    protected Sprite _deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        _points = points;
        _speed = speed;

        MAX_STEPS = Game.TILES_SIZE / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;

        _timeAfter = 20;
        _deadSprite = dead;
    }

    @Override
    public void update() {
        animate();

        if(!_alive) {
            afterKill();
            return;
        }

        if(_alive)
            calculateMove();
    }

    @Override
    public void render(Screen screen) {

        if(_alive)
            chooseSprite();
        else {
            if(_timeAfter > 0) {
                _sprite = _deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }

        }

        screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        int x_pos = 0;
        int y_pos = 0;
        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = MAX_STEPS;
        }

        if(_direction == 0) {
            y_pos--;
        }
        if(_direction == 2) {
            y_pos++;
        }
        if(_direction == 3) {
            x_pos--;
        }
        if(_direction == 1) {
            x_pos++;
        }

        if(canMove(x_pos, y_pos)) {
            _steps -= 1 + rest;
            move(x_pos * _speed, y_pos * _speed);
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if(!_alive) return;
        _y += ya;
        _x += xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr = _x, yr = _y - 16;

        if(_direction == 0) {
            yr += _sprite.getSize() -1;
            xr += _sprite.getSize()/2;
        }
        if(_direction == 1) {
            yr += _sprite.getSize()/2;
            xr += 1;
        }
        if(_direction == 2) {
            xr += _sprite.getSize()/2;
            yr += 1;
        }
        if(_direction == 3) {
            xr += _sprite.getSize() -1;
            yr += _sprite.getSize()/2;
        }

        int x_locate = Coordinates.pixelToTile(xr) +(int)x;
        int y_locate = Coordinates.pixelToTile(yr) +(int)y;

        Entity a = _board.getEntity(x_locate, y_locate, this);

        return a.collide(this);
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Explosion){
            this.kill();
            return false;
        }
        if(e instanceof Bomber){
            ((Bomber)e).kill();
            return false;
        }
        return true;
    }

    @Override
    public void kill() {
        if(!_alive) return;
        _alive = false;
        _board.addPoints(_points);
    }


    @Override
    protected void afterKill() {
        if (_timeAfter > 0) {
            _timeAfter--;
        } else {
            if (_finalAnimation > 0) {
                _finalAnimation--;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseSprite();
}
