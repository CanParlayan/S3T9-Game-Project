package rooms;

import enemies.Ghost;
import enemies.Ghost;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class GhostRoom extends Room {
    public GhostRoom(Enemy currentEnemy){
        super("You're in the Ghost Room", new Ghost(Game.chainmailArmor));
        HashSet<Item> ghostRoomItems = new HashSet<>();
        ghostRoomItems.add(Game.pot);
        setItems(ghostRoomItems);
    }
}