package rooms;

import enemies.Assassin;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class AssassinRoom extends Room {
    public AssassinRoom(Enemy currentEnemy){
        super("You're in the Assassin Room", new Assassin(Game.longSword));
        HashSet<Item> assassinRoomItems = new HashSet<>();
        assassinRoomItems.add(Game.pot);
        setItems(assassinRoomItems);
    }
}