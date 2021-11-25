package enemies;

import objects.Enemy;
import objects.Item;

public class Assassin extends Enemy {
    public Assassin(Item dropsItem) {
        super( "Assassin",150, 45, dropsItem);
    }
}
