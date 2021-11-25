package enemies;

import objects.Enemy;
import objects.Item;

public class Orc extends Enemy {
    public Orc(Item dropsItem) {
        super("Orc",105, 45, dropsItem);
    }
}
