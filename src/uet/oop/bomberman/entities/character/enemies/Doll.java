package uet.oop.bomberman.entities.character.enemies;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemies.AI.RandomAI;
import uet.oop.bomberman.entities.character.enemies.AI.TrackingAI;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Board board) {
        super(x, y, board, Sprite.doll_dead, 1.0, 150, 1);
        _sprite = Sprite.doll_left1;

        _ai = new TrackingAI(_board.getBomber(), this);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
                break;
        }
    }
}
