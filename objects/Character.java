package objects;

import items.HpPotion;

import java.util.ArrayList;

public class Character {
    private ArrayList<HpPotion> HpPotions = new ArrayList<>();
    private String name;
    private int hp = 100;
    private int armor = 0;
    private Item weaponInHand;
    private Armor currentlyWornArmor;
    private int playerDamage;

    public int getPlayerDamage() {
        return playerDamage;
    }

    public int setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
        return playerDamage;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public int getArmor() {
        return armor;
    }

    public int setArmor(int armor) {
        this.armor = armor;
        return armor;
    }

    public Armor getCurrentlyWornArmor() {
        return currentlyWornArmor;
    }

    public void setCurrentlyWornArmor(Armor currentlyWornArmor) {
        this.currentlyWornArmor = currentlyWornArmor;
    }

    public Item getWeaponInHand() {
        return weaponInHand;
    }

    public void setWeaponInHand(Item weaponInHand) {
        this.weaponInHand = weaponInHand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int setHp(int hp) {
        this.hp = hp;
        return hp;
    }

    public void addHp(int hp) {
        this.hp += hp;
        if (this.hp > 100) this.hp = 100;
    }

    public int getCurrHPPotionAmount() {
        return HpPotions.size();
    }

    public void addPotion(HpPotion Potion) {
        HpPotions.add(Potion);
    }

    public void removePotion(HpPotion p) {
        if (HpPotions.size() > 0) {
            HpPotions.remove(0);
        }
    }
}



