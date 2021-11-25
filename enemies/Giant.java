package enemies;

import objects.Enemy;
import objects.Item;

public class Giant extends Enemy {
    public Giant(Item dropsItem) {
        super("Giant",110, 55, dropsItem);
    }
}
