package uet.oop.bomberman.entities.character.enemies.AI;

public class RandomAI extends AI {

    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
