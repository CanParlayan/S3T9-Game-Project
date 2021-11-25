package rooms;

import enemies.Skeleton;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class SkeletonRoom extends Room {
    public SkeletonRoom(Enemy currentEnemy){
        super("You're in the Skeleton Room", new Skeleton(Game.shortSword));
        HashSet<Item> skeletonRoomItems = new HashSet<>();
        skeletonRoomItems.add(Game.broadAxe);
        setItems(skeletonRoomItems);
    }
}