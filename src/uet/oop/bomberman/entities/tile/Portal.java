package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    protected GameComponents _gameComponents;

    public Portal(int x, int y, GameComponents gameComponents, Sprite sprite) {
        super(x, y, sprite);
        _gameComponents = gameComponents;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {

            if (!_gameComponents.detectNoEnemies())
                return false;
            if (e.getXTile() == getX() && e.getYTile() == getY()) {
                if (_gameComponents.detectNoEnemies()) {
                    _gameComponents.nextLevel();
                    Game.audioPlay("clear.wav", false);
                }
            }

            return true;
        }

        return true;
    }

}
