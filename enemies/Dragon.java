package enemies;

import objects.Enemy;
import objects.Item;

public class Dragon extends Enemy {
    public Dragon(Item dropsItem) {
        super("Dragon",200, 70, dropsItem);
    }

}