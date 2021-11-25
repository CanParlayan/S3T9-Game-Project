package enemies;

import objects.Enemy;
import objects.Item;

public class Elf extends Enemy {
    public Elf(Item dropsItem) {
        super("Elf",130, 55, dropsItem);
    }
}
