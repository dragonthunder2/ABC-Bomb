package uet.oop.bomberman.entities.BombSet;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {
    public int explodeTime = 20;
    protected double countDown = 125;

    protected Board board;
    protected Explosion[] explosions;

    protected boolean explosion = false;
    protected boolean ableToPass = true;

    public Bomb(int x, int y, Board board) {
        this._x = x;
        this._y = y;
        this.board = board;
        this._sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if(countDown > 0)
            countDown--;
        else {
            if(!explosion)
                explode();
            else
                updateFlames();

            if(explodeTime > 0)
                explodeTime--;
            else
                remove();
        }

        animate();
    }

    @Override
    public void render(Screen screen) {
        if(explosion) {
            _sprite =  Sprite.bomb_exploded2;
            renderExplosion(screen);
        } else
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

        int xt = (int)_x << 4;
        int yt = (int)_y << 4;

        screen.renderEntity(xt, yt , this);
    }

    public void renderExplosion(Screen screen) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(screen);
        }
    }

    public void updateFlames() {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].update();
        }
    }

    /**
     * Xử lý Bomb nổ
     */
    protected void explode() {//nổ
        //Game.audioPlay("explosion.wav", false);
        explosion = true;
        ableToPass = true;
        Character x = board.getCharacterAtExcluding((int)_x, (int)_y, null);
        if(x != null){
            x.kill();
        }
        explosions = new Explosion[4];
        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new Explosion((int)_x, (int)_y, i, Game.getBombRadius(), board);
        }
        Game.audioPlay("explosion.wav", false);
    }

    public void timeExplode() {
        countDown = 0;
    }

    public ExplosionSection ExplosionIn(int x, int y) {
        if(!explosion) return null;

        for (int i = 0; i < explosions.length; i++) {
            if(explosions[i] == null) return null;
            ExplosionSection e = explosions[i].explosionSectionIn(x, y);
            if(e != null) return e;
        }

        return null;
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            double diffX = e.getX() - Coordinates.tileToPixel(getX());
            double diffY = e.getY() - Coordinates.tileToPixel(getY());

            if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) {
                ableToPass = false;
            }

            return ableToPass;
        }
        if(e instanceof Explosion ) {
            timeExplode();
            return true;
        }
        return false;
    }
}
