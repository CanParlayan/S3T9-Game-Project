package objects;

import objects.Item;

import java.util.HashSet;

public class Inventory {
    private HashSet<Item> items;

    public Inventory() {
        items = new HashSet<>();
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(String itemName) {
        items.add(Game.allItems.get(itemName));
    }


    public void remove(Item item) {
        items.remove(item);
    }

    public void remove(String itemName) {
        items.remove(Game.allItems.get(itemName));
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public boolean contains(String itemName) {
        return items.contains(Game.allItems.get(itemName));
    }

    public void printItems(String outputFormatString) {
        for(Item item: items) {
            System.out.printf(outputFormatString, item.getItemName());
        }
    }

    public boolean isEmpty() {
        if(items.size() == 0) return true;
        else return false;
    }

    public void setItems(HashSet<Item> items) {
        this.items = items == null ? new HashSet<>() : items;
    }

    public HashSet<Item> getItems() {
        return items;
    }
}
