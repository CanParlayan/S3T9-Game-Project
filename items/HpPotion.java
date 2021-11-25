package items;

import objects.Item;

import java.util.Scanner;

public class HpPotion extends Item {
    Scanner scan = new Scanner(System.in);
    private int hpPotionHealAmount;
    private int hpPotionDropChance;

    public HpPotion(String itemName, String itemDescrip, int hpPotionHealAmount, int hpPotionDropChance, int value) {
        super(itemName, itemDescrip, 0, 10, 3.5, 0);
        this.hpPotionHealAmount = hpPotionHealAmount;
        this.hpPotionDropChance = hpPotionDropChance;
    }

    public int getHpPotionDropChance() {
        return hpPotionDropChance;
    }

    public void setHpPotionDropChance(int hpPotionDropChance) {
        this.hpPotionDropChance = hpPotionDropChance;
    }

    public int getHpPotionHealAmount() {
        return hpPotionHealAmount;
    }

    public void setHpPotionHealAmount(int hpPotionHealAmount) {
        this.hpPotionHealAmount = hpPotionHealAmount;
    }
}
