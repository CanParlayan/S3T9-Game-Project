package objects;

import java.util.Scanner;

public class Hero extends Character {

    protected String gender;
    Scanner input = new Scanner(System.in);
    Inventory inventory = new Inventory();

    public Hero() {
        System.out.println("Please enter your name.");
        setName(input.next());
        //   try()
        setHp(100);
        System.out.println("Please choose your gender. \n Press 1 for Male \n Press 2 for Female. \n Press 3 if you do not prefer to say.");
        int choice = input.nextInt();
        if (choice == 1)
            System.out.println("Gender: Male");
        else if (choice == 2)
            System.out.println("Gender: Female");
        else
            System.out.println("Gender: Hero does not want to say.");

    }

    public void removeItem(String itemName) {
        inventory.remove(itemName);
    }

    public void addItem(String itemName) {
        inventory.add(itemName);
    }
   // public void changeWeaponInHand(String itemName){
     //   inventory.
    //}

    public boolean containsItem(String itemName) {
        return inventory.contains(itemName);
    }

    public boolean isEmpty() {

        if (inventory.getItems().size() != 0) {
            return false;
        }
        return true;
    }
}
