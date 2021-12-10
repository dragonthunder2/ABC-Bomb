package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class BombermanGame {

    public static void main(String[] args) {
        Game.audioPlay("ingame.wav", true);
        new Frame();
    }
}
