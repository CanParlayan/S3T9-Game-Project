package objects;

public class Hero extends Character {

    protected String gender;
    Inventory inventory = new Inventory();

    public Hero() {
        setHp(100);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void removeItem(String itemName) {
        inventory.remove(itemName);
    }

    public void addItem(String itemName) {
        inventory.add(itemName);
    }

    public boolean containsItem(String itemName) {
        return inventory.contains(itemName);
    }

    public boolean isEmpty() {
        return inventory.getItems().size() == 0;
    }
}
