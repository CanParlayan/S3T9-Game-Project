package objects;

import items.HpPotion;

import java.util.ArrayList;
import java.util.HashSet;

public class Room {
    private String description;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private Enemy currentEnemy;
    private Inventory inventory;

    public Room(String description, Enemy currentEnemy) {
        setDescription(description);
        this.currentEnemy = currentEnemy;
        inventory = new Inventory();
    }

    public void printDescription() {
        System.out.println(getDescription());
    }

    public void printDirections() {
        if(canMoveNorth()) {
            System.out.println("The door to the north is open.");
        }
        if(canMoveEast()) {
            System.out.println("The door to the east is open.");
        }
        if(canMoveSouth()) {
            System.out.println("The door to the south is open.");
        }
        if(canMoveWest()) {
            System.out.println("The door to the west is open.");
        }
    }

    public void printItems() {
        inventory.printItems("You see a %s.\n");
    }

    public boolean canMoveNorth() {
        return north != null;
    }

    public boolean canMoveEast() {
        return east != null;
    }

    public boolean canMoveSouth(){
        return south != null;
    }

    public boolean canMoveWest() {
        return west != null;
    }

    public boolean containsItem(String itemName) {
        return inventory.contains(itemName);
    }
    public boolean containsPot(HpPotion potion) {
        return inventory.contains(potion);
    }

    public void removeItem(String itemName) {
        inventory.remove(itemName);
    }
    public void removePot(HpPotion potion) {
        inventory.remove(potion);
    }

    public void addItem(String itemName) {
        inventory.add(itemName);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }


    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setItems(HashSet<Item> items) {
        inventory.setItems(items);
    }

    public String getDescription() {
        return description;
    }

    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getSouth() {
        return south;
    }

    public Room getWest() {
        return west;
    }

    public HashSet<Item> getItems() {
        return inventory.getItems();
    }

    public void setEnemy(Enemy enemy){
        this.currentEnemy = enemy;
    }
    public Enemy getEnemy() { return this.currentEnemy; }
}