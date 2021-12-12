package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class CoinItem extends Tile {
    protected GameComponents _gameComponents;

    public CoinItem(int x, int y, GameComponents gameComponents, Sprite sprite) {
        super(x, y, sprite);
        _gameComponents = gameComponents;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            Game.audioPlay("Item.wav", false);
            Game.getBoard().addPoints(50);
            remove();
        }
        return true;
    }

}
