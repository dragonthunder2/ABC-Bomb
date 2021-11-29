package uet.oop.bomberman.entities.character.enemies;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.BombSet.Explosion;
import uet.oop.bomberman.entities.Entity;
//import uet.oop.bomberman.entities.Message;
//import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
//import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {
    protected int _point;
    protected double _speed;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double _steps;

    protected int _finalAnimation = 30;
    protected Sprite _deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);
        _point = points;
        _speed = speed;

        MAX_STEPS = Game.TILES_SIZE/_speed;
        rest = (MAX_STEPS - (int)MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;

        _timeAfter = 20;
        _deadSprite = dead;
    }

    @Override
    public void update() {
        animate();

        if (!_alive) {
            afterKill();
            return;
        } else {
            calculateMove();
        }
    }

    @Override
    public void render(Screen screen) {
        if (_alive) {
            chooseSprite();
        } else {
            if (_timeAfter > 0) {
                _sprite = _deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 0);
            }
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    @Override
    protected void calculateMove() {

    }

    @Override
    protected void move(double xa, double ya) {

    }

    @Override
    protected boolean canMove(double x, double y) {
        return false;
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Explosion){
            this.kill();
            return false;
        }
        if(e instanceof Bomber){
            ((Bomber) e).kill();
            return false;
        }
        return true;
    }

    @Override
    public void kill() {
        if(!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {


    }

    protected abstract void chooseSprite();
}
