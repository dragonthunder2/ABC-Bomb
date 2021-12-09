package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class CoinItem extends Tile {
    protected Board _board;

    public CoinItem(int x, int y, Board board, Sprite sprite) {
        super(x, y, sprite);
        _board = board;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            Game.getBoard().addPoints(50);
            remove();
        }
        return true;
    }

}
