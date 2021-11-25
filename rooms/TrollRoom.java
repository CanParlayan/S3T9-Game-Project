package rooms;

import enemies.Troll;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class TrollRoom extends Room {
    public TrollRoom(Enemy currentEnemy){
        super("You're in the Troll Room", new Troll(Game.leatherArmor));
        HashSet<Item> trollRoomItems = new HashSet<>();
        trollRoomItems.add(Game.pot);
        setItems(trollRoomItems);
    }
}