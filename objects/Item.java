package objects;


public class Item {
    private final String itemName;
    private final String itemDescrip;
    private final int damage;
    private final double value;
    private final double weight;
    private final double range;

    public Item(String itemName, String itemDescrip, int damage, double value, double weight, double range) {
        this.itemName = itemName;
        this.itemDescrip = itemDescrip;
        this.damage = (int) (damage/range);
        this.value = value;
        this.weight = weight;
        this.range = range;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescrip() {
        return itemDescrip;
    }

    public int getDamage() {
        return damage;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public double getRange() {
        return range;
    }

}

