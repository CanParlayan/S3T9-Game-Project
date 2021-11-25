package enemies;

import objects.Enemy;
import objects.Item;

public class Spider extends Enemy {
    public Spider(Item dropsItem) {
        super("Spider",100, 45, dropsItem);
    }
}
