package rooms;

import enemies.Zombie;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class ZombieRoom extends Room {
    public ZombieRoom(Enemy currentEnemy){
        super("You're in the Zombie Room", new Zombie(Game.dagger));
        HashSet<Item> zombieRoomItems = new HashSet<>();
        zombieRoomItems.add(Game.pot);
        setItems(zombieRoomItems);
    }
}