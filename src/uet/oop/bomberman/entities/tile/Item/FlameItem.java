package uet.oop.bomberman.entities.tile.Item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

    public FlameItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            Game.audioPlay("Item.wav", false);
            Game.addBombRadius(1);
            remove();
        }
        return false;
    }
}
