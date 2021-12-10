package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.graphics.Screen;

public abstract class Character extends Animation {
	
	protected Board board;
	protected int direction = -1;
	protected boolean live = true;
	protected boolean moving = false;
	public int timeAfter = 40;
	
	public Character(int x, int y, Board board) {
		_x = x;
		_y = y;
		this.board = board;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);

	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);

	public abstract void kill();

	protected abstract void afterKill();

	protected abstract boolean canMove(double x, double y);

}
