package enemies;

import objects.Enemy;
import objects.Item;

public class Wizard extends Enemy {
    public Wizard(Item dropsItem) {
        super("Wizard",120, 55, dropsItem);
    }
}
