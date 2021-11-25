package enemies;

import objects.Enemy;
import objects.Item;

public class Zombie extends Enemy {
    public Zombie(Item dropsItem) {
        super("Zombie",80, 15, dropsItem);
    }
}
