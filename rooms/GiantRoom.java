package rooms;

import enemies.Giant;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class GiantRoom extends Room {
    public GiantRoom(Enemy currentEnemy){
        super("You're in the Giant Room", new Giant(Game.smallAxe));
        HashSet<Item> giantRoomItems = new HashSet<>();
        giantRoomItems.add(Game.pot);
        setItems(giantRoomItems);
    }
}