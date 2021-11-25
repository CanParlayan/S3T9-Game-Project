package enemies;

import objects.Enemy;
import objects.Item;

public class Beast extends Enemy {
    public Beast(Item dropsItem) {
        super("Beast",140, 40, dropsItem);
    }
}
