package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import uet.oop.bomberman.GameComponents;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemies.Balloon;
import uet.oop.bomberman.entities.character.enemies.Doll;
import uet.oop.bomberman.entities.character.enemies.Oneal;
import uet.oop.bomberman.entities.tile.CoinItem;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Item.BombItem;
import uet.oop.bomberman.entities.tile.Item.FlameItem;
import uet.oop.bomberman.entities.tile.Item.SpeedItem;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.graphics.Location;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLoadLevel extends LevelLoader {

    private static char[][] _map;

    public FileLoadLevel(GameComponents gameComponents, int level)  {
        super(gameComponents, level);
    }

    @Override
    public void loadLevel(int level) {

        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        _level = Integer.parseInt(arrays[0]);
        _height = Integer.parseInt(arrays[1]);
        _width = Integer.parseInt(arrays[2]);
        _map = new char[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                _map[i][j] = list.get(i + 1).charAt(j);
            }
        }
    }

    @Override
    public void createEntities() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int pos = x + y * getWidth();
                char c = _map[y][x];
                switch (c) {
                    case '#':
                        _gameComponents.addEntity(pos, new Wall(x, y, Sprite.wall));
                        break;
                    case '*':
                        _gameComponents.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 'p':
                        _gameComponents.addCharacter(new Bomber(Location.tileToPixel(x), Location.tileToPixel(y) + Game.TILES_SIZE, _gameComponents));
                        Screen.setOffset(0, 0);
                        _gameComponents.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '1':
                        _gameComponents.addCharacter(new Balloon(Location.tileToPixel(x), Location.tileToPixel(y) + Game.TILES_SIZE, _gameComponents));
                        _gameComponents.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '2':
                        _gameComponents.addCharacter(new Doll(Location.tileToPixel(x), Location.tileToPixel(y) + Game.TILES_SIZE, _gameComponents));
                        _gameComponents.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '3':
                        _gameComponents.addCharacter(new Oneal(Location.tileToPixel(x), Location.tileToPixel(y) + Game.TILES_SIZE, _gameComponents));
                        _gameComponents.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    case 'b':
                        LayeredEntity layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new BombItem(x, y, Sprite.powerup_bombs),
                                new Brick(x, y, Sprite.brick));
                        _gameComponents.addEntity(pos, layer);
                        break;
                    case 'f':
                        layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick));
                        _gameComponents.addEntity(pos, layer);
                        break;
                    case 's':
                        layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick));
                        _gameComponents.addEntity(pos, layer);
                        break;
                    case 'c':
                        _gameComponents.addEntity(pos, new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new CoinItem(x, y, _gameComponents, Sprite.coin)));
                        break;
                    case 'x':
                        _gameComponents.addEntity(pos, new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Portal(x, y, _gameComponents, Sprite.portal),
                                new Brick(x, y, Sprite.brick)));
                        break;

                    default:
                        _gameComponents.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                }
            }
        }
    }
}
