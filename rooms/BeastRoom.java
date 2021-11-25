package rooms;

import enemies.Beast;

import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class BeastRoom extends Room {
    public BeastRoom(Enemy currentEnemy){
        super("You're in the Beast Room", new Beast(Game.longSword));
        HashSet<Item> beastRoomItems = new HashSet<>();
        beastRoomItems.add(Game.pot);
        setItems(beastRoomItems);
    }
}