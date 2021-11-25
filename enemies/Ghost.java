package enemies;

import objects.Enemy;
import objects.Item;

public class Ghost extends Enemy {
    public Ghost(Item dropsItem) {
        super("Ghost",60, 20, dropsItem);
    }
}
