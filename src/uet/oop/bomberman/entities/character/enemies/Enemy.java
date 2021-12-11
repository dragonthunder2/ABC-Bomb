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

public abstract class Enemy extends Character {
    protected int points;
    protected double speed;
    protected AI ai;
    protected int lives;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;

    protected int finalAnimation = 30;
    protected Sprite deadSprite;
    protected int count = 0;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points, int lives) {
        super(x, y, board);

        this.points = points;
        this.speed = speed;
        this.lives = lives;

        MAX_STEPS = Game.TILES_SIZE / this.speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;

        timeAfter = 20;
        deadSprite = dead;
    }

    @Override
    public void update() {
        animate();

        if (!live) {
            afterKill();
            return;
        }

        if (live)
            calculateMove();
    }

    @Override
    public void render(Screen screen) {

        if (live)
            chooseSprite();
        else {
            if (timeAfter > 0) {
                _sprite = deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }

        }

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        int x_pos = 0;
        int y_pos = 0;
        if (steps <= 0) {
            direction = ai.AIMovements();
            steps = MAX_STEPS;
        }

        if (direction == 0) {
            y_pos--;
        }
        if (direction == 2) {
            y_pos++;
        }
        if (direction == 3) {
            x_pos--;
        }
        if (direction == 1) {
            x_pos++;
        }

        if (canMove(x_pos, y_pos)) {
            steps -= 1 + rest;
            move(x_pos * speed, y_pos * speed);
            moving = true;
        } else {
            steps = 0;
            moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if (!live) return;
        _y += ya;
        _x += xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr = _x, yr = _y - 16;

        if (direction == 0) {
            yr += _sprite.getSize() - 1;
            xr += _sprite.getSize() / 2;
        }
        if (direction == 1) {
            yr += _sprite.getSize() / 2;
            xr += 1;
        }
        if (direction == 2) {
            xr += _sprite.getSize() / 2;
            yr += 1;
        }
        if (direction == 3) {
            xr += _sprite.getSize() - 1;
            yr += _sprite.getSize() / 2;
        }

        int x_locate = Coordinates.pixelToTile(xr) + (int) x;
        int y_locate = Coordinates.pixelToTile(yr) + (int) y;

        Entity a = board.getEntity(x_locate, y_locate, this);

        return a.collide(this);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Explosion) {
            count++;
            if (count == lives) {
                this.kill();
                return false;
            }
        }
        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return false;
        }
        return true;
    }

    @Override
    public void kill() {
        Game.audioPlay("kill.wav", false);
        live = false;
        board.addPoints(points);
    }


    @Override
    protected void afterKill() {
        if (timeAfter > 0) {
            timeAfter--;
        } else {
            if (finalAnimation > 0) {
                finalAnimation--;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseSprite();
}
