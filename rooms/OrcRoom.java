package rooms;

import enemies.Orc;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class OrcRoom extends Room {
    public OrcRoom(Enemy currentEnemy){
        super("You're in the Orc Room", new Orc(Game.shortbow));
        HashSet<Item> orcRoomItems = new HashSet<>();
        orcRoomItems.add(Game.pot);
        setItems(orcRoomItems);
    }
}