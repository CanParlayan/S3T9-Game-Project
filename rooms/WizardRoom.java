package rooms;

import enemies.Wizard;
import objects.Enemy;
import objects.Game;
import objects.Item;
import objects.Room;

import java.util.HashSet;

public class WizardRoom extends Room {
    public WizardRoom(Enemy currentEnemy){
        super("You're in the Wizard Room", new Wizard(Game.pot));
        HashSet<Item> wizardRoomItems = new HashSet<>();
        wizardRoomItems.add(Game.crossbow);
        setItems(wizardRoomItems);
    }
}