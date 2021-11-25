package enemies;

import objects.Enemy;
import objects.Item;

public class Vampire extends Enemy {
    public Vampire(Item dropsItem) {
        super("Vampire",80, 15, dropsItem);
    }
}
