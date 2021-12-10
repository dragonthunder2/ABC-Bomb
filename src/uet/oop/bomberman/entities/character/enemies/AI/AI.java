package uet.oop.bomberman.entities.character.enemies.AI;

import java.util.Random;

public abstract class AI {

    protected Random random = new Random();

    public abstract int AIMovements();
}
