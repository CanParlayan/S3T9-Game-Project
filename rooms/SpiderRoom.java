package rooms;

import enemies.Spider
        ;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class SpiderRoom extends Room {
    public SpiderRoom(Enemy currentEnemy){
        super("You're in the Spider Room", new Spider(Game.shortbow));
        HashSet<Item> spiderRoomItems = new HashSet<>();
        spiderRoomItems.add(Game.pot);
        setItems(spiderRoomItems);
    }
}