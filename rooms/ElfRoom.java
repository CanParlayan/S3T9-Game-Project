package rooms;

import enemies.Elf;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class ElfRoom extends Room {
    public ElfRoom(Enemy currentEnemy){
        super("You're in the Elf Room", new Elf(Game.crossbow));
        HashSet<Item> elfRoomItems = new HashSet<>();
        elfRoomItems.add(Game.pot);
        setItems(elfRoomItems);
    }
}