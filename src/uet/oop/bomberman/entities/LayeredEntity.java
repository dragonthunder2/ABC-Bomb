package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;

public class LayeredEntity extends Entity {

    protected LinkedList<Entity> entityLinkedList = new LinkedList<>();

    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    public LayeredEntity(int x, int y, Entity... entities) {
        _x = x;
        _y = y;

        for (int i = 0; i < entities.length; i++) {
            entityLinkedList.add(entities[i]);

            if (i > 1) {
                if (entities[i] instanceof Wall)
                    ((Wall) entities[i]).addBelowSprite(entities[i - 1].getSprite());
            }
        }
    }

    public Entity getTopEntity() {
        return entityLinkedList.getLast();
    }

    private void clearRemoved() {
        Entity top = getTopEntity();

        if (top.isRemoved()) {
            entityLinkedList.removeLast();
        }
    }

    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    @Override
    public boolean collide(Entity e) {
        return getTopEntity().collide(e);
    }

}
