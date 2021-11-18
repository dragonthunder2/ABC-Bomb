package uet.oop.bomberman.entities.character.enemies;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, 0.8, 100);
        _sprite = Sprite.balloom_left1;
    }

    @Override
    protected void chooseSprite() {
        _sprite = Sprite.oneal_left1;
    }
}
