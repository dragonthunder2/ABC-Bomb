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
        if (b == null) {
            return random.nextInt(4);
        }

        int select = random.nextInt(2);
        int[] a = new int[2];
        if(b.getXTile() < e.getXTile())
            a[0] = 3;
        else if(b.getXTile() > e.getXTile())
            a[0] = 1;
        else
            a[0] = -1;
        if(b.getYTile() < e.getYTile())
            a[1] = 0;
        else if(b.getYTile() > e.getYTile())
            a[1] = 2;
        else
            a[1] = -1;
        return a[select];
    }
}
