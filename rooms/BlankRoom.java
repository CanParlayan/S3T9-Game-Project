package rooms;

import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class BlankRoom extends Room {
    public BlankRoom(){
        super("There is nothing in this room", null);
        HashSet<Item> blankroom = new HashSet<>();
        setItems(blankroom);
    }
}