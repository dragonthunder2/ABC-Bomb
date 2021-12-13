package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.graphics.Screen;

public abstract class Character extends Animation {
	
	protected GameComponents gameComponents;
	protected int direction = -1;
	protected boolean live = true;
	protected boolean moving = false;
	public int timeAfter = 40;
	
	public Character(int x, int y, GameComponents gameComponents) {
		_x = x;
		_y = y;
		this.gameComponents = gameComponents;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);

	protected abstract boolean canMove(double x, double y);

	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);

	public abstract void kill();

	protected abstract void afterKill();

}
