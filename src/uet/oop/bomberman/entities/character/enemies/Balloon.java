package uet.oop.bomberman.entities.character.enemies;

import static java.lang.Math.random;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
//import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, 0.5, 100);
        _sprite = Sprite.balloom_left1;
    }

    @Override
    protected void chooseSprite() {
        _sprite = Sprite.balloom_left1;
    }
}
