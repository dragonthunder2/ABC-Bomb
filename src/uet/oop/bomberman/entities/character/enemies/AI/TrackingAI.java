package uet.oop.bomberman.entities.character.enemies.AI;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemies.Enemy;

public class TrackingAI extends AI {
    Bomber b;
    Enemy e;

    public TrackingAI(Bomber b, Enemy e) {
        this.b = b;
        this.e = e;
    }

    @Override
    public int calculateDirection() {
        return 0;
    }
}
