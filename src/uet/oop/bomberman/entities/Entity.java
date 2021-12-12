package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.GraphicInterface;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Location;

public abstract class Entity implements GraphicInterface {

	protected Sprite _sprite;
	protected double _x, _y;
	protected boolean _removed = false;

	public void remove() {
		_removed = true;
	}

	public boolean isRemoved() {
		return _removed;
	}

	public Sprite getSprite() {
		return _sprite;
	}

	public abstract boolean collide(Entity e);

	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	public int getXTile() {
		return Location.pixelToTile(_x + _sprite.SIZE / 2);
	}

	public int getYTile() {
		return Location.pixelToTile(_y - _sprite.SIZE / 2);
	}

	@Override
	public abstract void update();

	@Override
	public abstract void render(Screen screen);


    
}
