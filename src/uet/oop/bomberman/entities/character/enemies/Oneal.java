package uet.oop.bomberman.entities.character.enemies;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemies.AI.TrackingAI;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, GameComponents gameComponents) {
        super(x, y, gameComponents, Sprite.oneal_dead, Game.getBomberSpeed(), 200, 2);
        _sprite = Sprite.oneal_left1;

        ai = new TrackingAI(this.gameComponents.getBomber(), this);
        direction = ai.AIMovements();
    }

    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                break;
        }
    }
}
