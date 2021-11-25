package objects;


public class Armor extends Item {
    private final int giveArmor;

    public Armor(String itemName, String itemDescrip, int giveArmor, int value, int weight) {
        super(itemName, itemDescrip, 0, value, weight, 0);
        this.giveArmor = giveArmor;
    }

    public int getGiveArmor() {
        return giveArmor;
    }
}
