package uet.oop.bomberman.level;

import uet.oop.bomberman.GameComponents;

public abstract class LevelLoader {

	protected int _width = 20, _height = 20;
	protected int _level;
	protected GameComponents _gameComponents;

	public LevelLoader(GameComponents gameComponents, int level)  {
		_gameComponents = gameComponents;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) ;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
