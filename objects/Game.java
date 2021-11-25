package objects;

import enemies.*;
import items.*;

import java.io.*;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final String
            askUser = "What do you want to do?",
            welcomeUser = """

                    Welcome to the game.
                    !Please read!
                    You can move by typing go, leaving and then specifying the direction.(east,west,south,north)
                    You can use the words east,north,south,west to move around.
                    For example: go east, go south.
                    You can type inventory to open up your inventory.             
                    You can type attack to fight with the enemy inside that room.
                    To use a weapon, type use, leave a space and specify the weapon's name.
                    To wear an armor, type wear, leave a space and specify the armor's name.
                    To add an item to your inventory, type pick up, leave a space and specify an item name.
                    Specified item names must be written in lowercase and adjacent.
                    For example: pick up chainmailarmor, use dragonsword, wear snakearmor, pick up potion
                    You can type quit to exit the game.""";
    private static final String[]
            movement = {"go"}, directionNorth = {"north"}, directionEast = {"east"}, directionSouth = {"south"},
            directionWest = {"west"}, openInventory = {"inventory"}, attack = {"attack"}, stop = {"quit"};

    public static HashMap<String, Item> allWeapons = new HashMap<>();
    public static HashMap<String, Armor> allArmors = new HashMap<>();
    public static HashMap<String, Item> allItems = new HashMap<>();

    public static Item longbow = new Longbow();
    public static Item crossbow = new Crossbow();
    public static Armor chainmailArmor = new ChainmailArmor();
    public static Armor leatherArmor = new LeatherArmor();
    public static Item shortSword = new Shortsword();
    public static Item shortbow = new Shortbow();
    public static Item broadAxe = new BroadAxe();
    public static Item smallAxe = new SmallAxe();
    public static Item dragonSword = new DragonSword();
    public static Armor snakeArmor = new SnakeArmor();
    public static Armor lionArmor = new LionArmor();
    public static Item dagger = new Dagger();
    public static Item longSword = new Longsword();
    public static HpPotion pot = new HpPotion("Potion", "A little potion which gives health ",
            20, 40, 5);


    private final int potDropChance = 40;
    private int highScore = 0;
    Hero hero = new Hero();
    Scanner scan = new Scanner(System.in);
    Random rand = new Random();
    private objects.Inventory inventory;
    private int levelHeroKillCount = 0;
    private int totalDeadEnemyCount = 0;
    private int savedTownsPeopleCount = 0;
    private final int townsPeopleHealAmount = 5;
    private final int townsPeopleHealChance = 10;
    private Room currentRoom;
    boolean running = true;

    private void drinkPot() {
        if (hero.getHp() == 100) {
            System.out.println("You are at max health");
        } else if (hero.getCurrHPPotionAmount() > 0) {
            int temp;
            temp = hero.getHp();
            hero.addHp(pot.getHpPotionHealAmount());
            hero.removePotion(pot);
            System.out.println("You drank a health potion, it healed you for " + Math.min(pot.getHpPotionHealAmount(), 100 - temp)
                    + " HP" + "\nYou now have " + hero.getHp() + " HP."
                    + "\nYou have " + hero.getCurrHPPotionAmount() + " health potions left.");
        } else {
            System.out.println("You are out of health potions!" +
                    "\nDefeat an enemy for a chance to drop a heal");
        }
    }

    private void enemyPotDropChance() {
        if (rand.nextInt(100) < potDropChance && hero.getCurrHPPotionAmount() < 3) {
            hero.addPotion(pot);
            System.out.println(" # The " + currentRoom.getEnemy().getEnemyName() + " dropped a health potion! # ");
            System.out.println(" # You now have " + hero.getCurrHPPotionAmount() + " HP Potions! # ");
        }else{
            System.out.println("No potion dropped from the enemy");
        }
    }

    private void initializeItems() {
        allWeapons.put("shortsword", shortSword);
        allWeapons.put("longsword", longSword);
        allArmors.put("leatherarmor", chainmailArmor);
        allArmors.put("chainmailarmor", chainmailArmor);
        allWeapons.put("longbow", longbow);
        allWeapons.put("crossbow", crossbow);
        allWeapons.put("shortbow", shortbow);
        allWeapons.put("dragonsword", dragonSword);
        allWeapons.put("smallaxe", smallAxe);
        allWeapons.put("broadaxe", broadAxe);
        allWeapons.put("dagger", dagger);
        allArmors.put("snakearmor", snakeArmor);
        allArmors.put("lionarmor", lionArmor);

        allItems.putAll(allWeapons);
        allItems.putAll(allArmors);
    }
    public int getValues(){
        int score = 0;
        for(Item item : hero.inventory.getItems()){
            score += item.getValue();
        }
        return score;
    }

    private void gameStart() {
        hero.getName();
        System.out.println("Welcome to the game");

        System.out.println("Choose one starting weapon");
        System.out.println("1 for Shortbow");
        System.out.println("2 for Dagger");
        int i = scan.nextInt();
        switch (i) {
            case 1:
                hero.setWeaponInHand(shortbow);
                hero.inventory.add(shortbow);
                System.out.println("You will start with the " + hero.getWeaponInHand().getItemName() + " as your weapon");
                break;
            case 2:
                hero.setWeaponInHand(dagger);
                hero.inventory.add(dagger);
                System.out.println("You will start with the " + hero.getWeaponInHand().getItemName() + " as your weapon");
                break;
        }
        hero.setCurrentlyWornArmor(leatherArmor);
        hero.inventory.add(leatherArmor);
        System.out.println("You will start with the " + hero.getCurrentlyWornArmor().getItemName() + " as your armor");
        hero.addPotion(pot);
        hero.addPotion(pot);
        hero.setPlayerDamage(hero.getWeaponInHand().getDamage());
        hero.setArmor((hero.getCurrentlyWornArmor()).getGiveArmor());
        hero.setHp(hero.getHp());

        System.out.println(hero.getWeaponInHand().getItemName() + " can deal " + hero.getPlayerDamage() + " damage");
        System.out.println(hero.getCurrentlyWornArmor().getItemName() + " can protect you from "
                + hero.getArmor() + " damage");
        if (hero.getCurrHPPotionAmount() == 1) {
            System.out.println("You have " + hero.getCurrHPPotionAmount() + " potion");
        } else {
            System.out.println("You have " + hero.getCurrHPPotionAmount() + " potions");
        }
        scan.nextLine();
    }

    private void parseInput(String input) {

        input = input.replaceAll("\\s+", " ");


        input = input.trim();


        String[] words = input.split(" ");


        if (words.length == 0) {
            return;
        }

        if (inputEquals(words, movement, directionNorth)) {
            if (currentRoom.canMoveNorth()) {
                currentRoom = currentRoom.getNorth();
                System.out.println("You moved north.");
            } else {
                System.out.println("You can't move north.");
            }
            return;
        }

        if (inputEquals(words, movement, directionEast)) {
            if (currentRoom.canMoveEast()) {
                currentRoom = currentRoom.getEast();
                System.out.println("You moved east.");
            } else {
                System.out.println("You can't move east.");
            }
            return;
        }


        if (inputEquals(words, movement, directionSouth)) {
            if (currentRoom.canMoveSouth()) {
                currentRoom = currentRoom.getSouth();
                System.out.println("You moved south.");
            } else {
                System.out.println("You can't move south.");
            }
            return;
        }

        if (inputEquals(words, movement, directionWest)) {
            if (currentRoom.canMoveWest()) {
                currentRoom = currentRoom.getWest();
                System.out.println("You moved west.");
            } else {
                System.out.println("You can't move west.");
            }
            return;
        }

        if (inputEquals(words, new String[]{"pick"}, new String[]{"up"})) {
            String itemName = words[2];
            for (int i = 3; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (currentRoom.containsItem(itemName)) {
                currentRoom.removeItem(itemName);
                hero.addItem(itemName);
                System.out.println("You picked up the " + itemName + ".");
            }
            if (hero.getCurrHPPotionAmount() == 3) {
                System.out.println("But your potion pouch is full you can't have anymore HP Potions");
            } else if (currentRoom.containsPot(pot)) {
                if (currentRoom.containsPot(pot)) {
                    hero.addPotion(pot);
                    currentRoom.removePot(pot);
                    System.out.println("You now have " + hero.getCurrHPPotionAmount() + " potions in your potion pouch");
                } else {
                    System.out.println("Please enter a valid answer");
                }
            }
        }

        if (inputEquals(words, new String[]{"use"})){
          String itemName = words[1];
          for (int i = 2; i < words.length; i++) {
              itemName += " " + words[i];
          }
          if (hero.inventory.contains(itemName)) {
              hero.setWeaponInHand(allWeapons.get(itemName));
              hero.setPlayerDamage(allWeapons.get(itemName).getDamage());
              System.out.println("You now have " + itemName + " in your hands.");
      } else {
          System.out.println("That weapon isn't available");
      }
      return;
          }
        if (inputEquals(words, new String[]{"wear"})){
            String itemName = words[2];
            for (int i = 3; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (hero.containsItem(itemName)) {
                hero.setCurrentlyWornArmor(allArmors.get(itemName));
                hero.setArmor(allArmors.get(itemName).getGiveArmor());
                System.out.println("You now wore " + itemName);
            } else {
                System.out.println("That armor isn't available");
            }
            return;
        }


            if (inputEquals(words, new String[]{"drop"})) {
                String itemName = words[1];
                for (int i = 2; i < words.length; i++) {
                    itemName += " " + words[i];
                }
                if (hero.containsItem(itemName)) {

                    hero.removeItem(itemName);

                    currentRoom.addItem(itemName);
                    hero.setPlayerDamage(0);
                    System.out.println("You dropped the " + itemName + ".");
                } else {
                    System.out.println("That item isn't available");
                }
                return;
            }
            if (inputEquals(words, attack)) {
                if (currentRoom.getEnemy() != null) {
                    System.out.println("You engaged in a fight with a " + currentRoom.getEnemy().getEnemyName());
                    System.out.println("You have " + hero.getHp() + " hp.");
                    System.out.println("Your weapon deals " + hero.getPlayerDamage() + " damage");
                    System.out.println(currentRoom.getEnemy().getEnemyName() + " can deal you " +
                            currentRoom.getEnemy().getEnemyDamage() + " damage");
                    while (hero.getHp() > 0 && currentRoom.getEnemy().getEnemyHealth() > 0) {
                        System.out.println(currentRoom.getEnemy().getEnemyName() + " has " +
                                currentRoom.getEnemy().getEnemyHealth() + " hp.");
                        System.out.println(askUser);
                        System.out.println("Type 1 for attacking the enemy");
                        System.out.println("Type 2 for drinking a health potion");
                        int fightInput = scan.nextInt();
                        switch (fightInput) {
                            case 1:
                                currentRoom.getEnemy().takeDamage(hero.getPlayerDamage());
                                hero.takeDamage(currentRoom.getEnemy().getEnemyDamage() - hero.getCurrentlyWornArmor().getGiveArmor());
                                System.out.println("Your armor protected you from " +
                                        hero.getCurrentlyWornArmor().getGiveArmor());
                                System.out.println("You have taken " + (currentRoom.getEnemy().getEnemyDamage() -
                                        hero.getCurrentlyWornArmor().getGiveArmor()) + " damage");
                                System.out.println("You gave enemy " + hero.getPlayerDamage() + " damage");
                                System.out.println("You have " + hero.getHp() + " hp.");
                                break;
                            case 2:
                                drinkPot();
                                break;
                            default:
                                System.out.println("Enter a valid command");
                        }
                    }
                    if (hero.getHp() <= 0) {

                        System.out.println("You died " + hero.getName());
                        System.out.println("Number of enemies you killed : " + totalDeadEnemyCount);
                        System.out.println("Number of people you saved : " + savedTownsPeopleCount);
                        highScore = this.getValues();
                        saveScore();
                        System.exit(1);

                    } else if (currentRoom.getEnemy().getEnemyHealth() <= 0) {
                        System.out.println("You killed the " + currentRoom.getEnemy().getEnemyName());
                        currentRoom.addItem(currentRoom.getEnemy().getDropsItem());
                        System.out.println("Enemy dropped " + currentRoom.getEnemy().getDropsItem().getItemName());

                        System.out.println("You saved a human from the hands of the " +
                                currentRoom.getEnemy().getEnemyName());
                        currentRoom.setEnemy(null);
                        levelHeroKillCount++;
                        totalDeadEnemyCount++;
                        savedTownsPeopleCount++;
                        if (rand.nextInt(100) < townsPeopleHealChance) {
                            System.out.println("The human you save gave you a food");
                            hero.setHp(hero.getHp() + townsPeopleHealAmount);
                            System.out.println("Food healed you for " + townsPeopleHealAmount + "hp.");

                        } else {
                            System.out.println("The person you saved thanked you.");
                        }
                    }
                    scan.nextLine();
                } else if (currentRoom.getEnemy() == null){
                    System.out.println("There is nothing to fight in this room");
                }
            }


            if (inputEquals(words, openInventory)) {
                System.out.println("Inventory:");
                if (hero.inventory.isEmpty()) {
                    System.out.println(" - There is nothing in here.");
                } else {
                    hero.inventory.printItems(" - %s\n");
                    System.out.println("** You have " +hero.getCurrHPPotionAmount() + " potions **");
                }
                return;
            }
            if (inputEquals(words, stop)) {
                System.out.println("You decided to run away and didn't accomplished what you have came to dungeon for");
                System.out.println("You left all your items while escaping");
                System.out.println("You will never be known as an hero. Those who know you will call you a coward");
                System.exit(1);
                return;
            }
        //  System.out.println("Please enter a valid command");

        }
        private static boolean inputEquals (String[]words, String[]...userInput){
            if (words.length < userInput.length) {
                return false;
            }
            for (int i = 0; i < userInput.length; i++) {
                String word = words[i];
                String[] possibleWords = userInput[i];
                if (!equalsAny(word, possibleWords)) {
                    return false;
                }
            }return true;
        }
        private static boolean equalsAny (String word, String[]possibleWords){
            for (String possibility : possibleWords) {
                if (word.equalsIgnoreCase(possibility)) {
                    return true;
                }
            }return false;
        }
        public void run () {
        Environment environment = new Environment();
        environment.initializeLevel1();
        environment.initializeLevel2();
        environment.initializeLevel3();
        environment.initializeLevel4();
        environment.initializeLevel5();
        environment.initializeLevel6();
        environment.initializeLevel7();
        environment.initializeLevel8();
        environment.initializeLevel9();
        environment.initializeLevel10();
        environment.initializeLevel11();
        environment.initializeLevel12();
        environment.initializeLevel13();
        environment.initializeLevel14();
        environment.initializeLevel15();
        environment.initializeLevel16();

            currentRoom = environment.getLevel1StartingRoom();

            initializeItems();
            gameStart();
            System.out.println(welcomeUser);
            System.out.println("You just got in the dungeon");
            while (running) {
                currentRoom.printDescription();
                currentRoom.printDirections();
              currentRoom.printItems();
                System.out.println(askUser);
                String start = scan.nextLine();
                parseInput(start);

                if(levelHeroKillCount == 3){
                    currentRoom = environment.goToNextLevel();
                    levelHeroKillCount = 0;
                }

                if(environment.getCurrentLevel() == 17){
                    saveScore();
                    System.exit(1);
                }

            }

        }
    public void saveScore() {
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        try {
            writeFile = new FileWriter("highscore.txt");
            writer = new BufferedWriter(writeFile);
            writer.write(highScore);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }


