package uet.oop.bomberman.entities;

public abstract class Animation extends Entity {

	protected int _animate = 0;
	protected final int MAX_ANIMATE = 8000;
	
	protected void animate() {
		if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
	}

}
