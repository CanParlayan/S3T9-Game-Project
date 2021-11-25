package rooms;

import enemies.Dragon;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class DragonRoom extends Room {
    public DragonRoom(Enemy currentEnemy){
        super("You're in the Dragon Room", new Dragon(Game.dragonSword));
        HashSet<Item> dragonRoomItems = new HashSet<>();
        dragonRoomItems.add(Game.pot);
        setItems(dragonRoomItems);
    }
}