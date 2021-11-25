package rooms;

import enemies.Wolf;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class WolfRoom extends Room {
    public WolfRoom(Enemy currentEnemy){
        super("You're in the Wolf Room", new Wolf(Game.broadAxe));
        HashSet<Item> wolfRoomItems = new HashSet<>();
        wolfRoomItems.add(Game.pot);
        setItems(wolfRoomItems);
    }
}