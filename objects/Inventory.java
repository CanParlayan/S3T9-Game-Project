package objects;

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

    public String formatItems(String outputFormatString) {
        StringBuilder builder = new StringBuilder();
        for (Item item : items) {
            builder.append(String.format(outputFormatString, item.getItemName()));
            if (!outputFormatString.endsWith("\n")) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void setItems(HashSet<Item> items) {
        this.items = items == null ? new HashSet<>() : items;
    }

    public HashSet<Item> getItems() {
        return items;
    }

}
