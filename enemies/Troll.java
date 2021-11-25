package enemies;

import objects.Enemy;
import objects.Item;

public class Troll extends Enemy {
    public Troll(Item dropsItem) {
        super("Troll",90, 25, dropsItem);
    }

}
