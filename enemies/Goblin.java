package enemies;

import objects.Enemy;
import objects.Item;

public class Goblin extends Enemy {
    public Goblin(Item dropsItem) {
        super("Goblin",100, 45, dropsItem);
    }
}
