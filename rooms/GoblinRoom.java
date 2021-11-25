package rooms;

import enemies.Goblin;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class GoblinRoom extends Room {
    public GoblinRoom(Enemy currentEnemy){
        super("You're in the Goblin Room", new Goblin(Game.longbow));
        HashSet<Item> goblinRoomItems = new HashSet<>();
        goblinRoomItems.add(Game.pot);
        setItems(goblinRoomItems);
    }
}