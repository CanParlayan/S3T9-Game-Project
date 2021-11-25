package enemies;

import objects.Enemy;
import objects.Item;

public class Skeleton extends Enemy {
    public Skeleton(Item dropsItem) {
        super("Skeleton",115, 50, dropsItem);
    }
}
