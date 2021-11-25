package rooms;

import enemies.Vampire;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class VampireRoom extends Room {
    public VampireRoom(Enemy currentEnemy){
        super("You're in the Vampire Room", new Vampire(Game.smallAxe));
        HashSet<Item> vampireRoomItems = new HashSet<>();
        vampireRoomItems.add(Game.pot);
        setItems(vampireRoomItems);
    }
}