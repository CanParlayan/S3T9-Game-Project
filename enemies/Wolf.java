package enemies;

import objects.Enemy;
import objects.Item;

public class Wolf extends Enemy {
    public Wolf(Item dropsItem) {
        super("Wolf",85, 20, dropsItem);
    }
}
