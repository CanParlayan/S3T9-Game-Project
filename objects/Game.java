package objects;

import enemies.*;
import items.*;
import objects.io.ConsoleGameIO;
import objects.io.GameIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Game {
    private static final String askUser = "What do you want to do?";
    private static final String welcomeUser = """
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
    public static HpPotion pot = new HpPotion("Potion", "A little potion which gives health ", 20, 40, 5);

    private final int potDropChance = 40;
    private int highScore = 0;
    Hero hero = new Hero();
    private final Random rand = new Random();
    private final GameIO io;
    private int levelHeroKillCount = 0;
    private int totalDeadEnemyCount = 0;
    private int savedTownsPeopleCount = 0;
    private final int townsPeopleHealAmount = 5;
    private final int townsPeopleHealChance = 10;
    private Room currentRoom;
    private boolean running = true;

    public Game() {
        this(new ConsoleGameIO());
    }

    public Game(GameIO io) {
        this.io = io;
    }

    private void drinkPot() {
        if (hero.getHp() == 100) {
            io.println("You are at max health");
        } else if (hero.getCurrHPPotionAmount() > 0) {
            int temp = hero.getHp();
            hero.addHp(pot.getHpPotionHealAmount());
            hero.removePotion(pot);
            io.println("You drank a health potion, it healed you for " + Math.min(pot.getHpPotionHealAmount(), 100 - temp)
                    + " HP" + "\nYou now have " + hero.getHp() + " HP."
                    + "\nYou have " + hero.getCurrHPPotionAmount() + " health potions left.");
            io.updateHero(hero);
        } else {
            io.println("You are out of health potions!"
                    + "\nDefeat an enemy for a chance to drop a heal");
        }
    }

    private void enemyPotDropChance() {
        if (rand.nextInt(100) < potDropChance && hero.getCurrHPPotionAmount() < 3) {
            hero.addPotion(pot);
            io.println(" # The " + currentRoom.getEnemy().getEnemyName() + " dropped a health potion! # ");
            io.println(" # You now have " + hero.getCurrHPPotionAmount() + " HP Potions! # ");
            io.updateHero(hero);
        } else {
            io.println("No potion dropped from the enemy");
        }
    }

    private void initializeItems() {
        allWeapons.put("shortsword", shortSword);
        allWeapons.put("longsword", longSword);
        allArmors.put("leatherarmor", leatherArmor);
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

    public int getValues() {
        int score = 0;
        for (Item item : hero.getInventory().getItems()) {
            score += item.getValue();
        }
        return score;
    }

    private void promptForHeroDetails() {
        io.println("Please enter your name.");
        hero.setName(io.readLine());
        hero.setHp(100);
        io.updateHero(hero);
        io.println("Please choose your gender. \n Press 1 for Male \n Press 2 for Female. \n Press 3 if you do not prefer to say.");
        int choice = io.readInt();
        switch (choice) {
            case 1 -> {
                hero.setGender("Male");
                io.println("Gender: Male");
            }
            case 2 -> {
                hero.setGender("Female");
                io.println("Gender: Female");
            }
            default -> {
                hero.setGender("Does not want to say");
                io.println("Gender: Hero does not want to say.");
            }
        }
        io.updateHero(hero);
    }

    private void gameStart() {
        promptForHeroDetails();
        io.println("Welcome to the game");

        io.println("Choose one starting weapon");
        io.println("1 for Shortbow");
        io.println("2 for Dagger");
        int i = io.readInt();
        switch (i) {
            case 1 -> {
                hero.setWeaponInHand(shortbow);
                hero.getInventory().add(shortbow);
                io.println("You will start with the " + hero.getWeaponInHand().getItemName() + " as your weapon");
            }
            case 2 -> {
                hero.setWeaponInHand(dagger);
                hero.getInventory().add(dagger);
                io.println("You will start with the " + hero.getWeaponInHand().getItemName() + " as your weapon");
            }
            default -> io.println("Invalid choice, defaulting to the Dagger.");
        }
        if (hero.getWeaponInHand() == null) {
            hero.setWeaponInHand(dagger);
            hero.getInventory().add(dagger);
        }
        hero.setCurrentlyWornArmor(leatherArmor);
        hero.getInventory().add(leatherArmor);
        io.println("You will start with the " + hero.getCurrentlyWornArmor().getItemName() + " as your armor");
        hero.addPotion(pot);
        hero.addPotion(pot);
        hero.setPlayerDamage(hero.getWeaponInHand().getDamage());
        hero.setArmor((hero.getCurrentlyWornArmor()).getGiveArmor());
        hero.setHp(hero.getHp());

        io.println(hero.getWeaponInHand().getItemName() + " can deal " + hero.getPlayerDamage() + " damage");
        io.println(hero.getCurrentlyWornArmor().getItemName() + " can protect you from "
                + hero.getArmor() + " damage");
        if (hero.getCurrHPPotionAmount() == 1) {
            io.println("You have " + hero.getCurrHPPotionAmount() + " potion");
        } else {
            io.println("You have " + hero.getCurrHPPotionAmount() + " potions");
        }
        io.updateHero(hero);
    }

    private void describeCurrentRoom() {
        if (currentRoom == null) {
            return;
        }
        io.println(currentRoom.getDescription());
        if (currentRoom.canMoveNorth()) {
            io.println("The door to the north is open.");
        }
        if (currentRoom.canMoveEast()) {
            io.println("The door to the east is open.");
        }
        if (currentRoom.canMoveSouth()) {
            io.println("The door to the south is open.");
        }
        if (currentRoom.canMoveWest()) {
            io.println("The door to the west is open.");
        }
        if (!currentRoom.getItems().isEmpty()) {
            for (Item item : currentRoom.getItems()) {
                io.println("You see a " + item.getItemName() + ".");
            }
        }
        io.updateRoom(currentRoom);
    }

    private void parseInput(String input) {
        if (input == null) {
            return;
        }
        input = input.replaceAll("\\s+", " ");
        input = input.trim();

        if (input.isEmpty()) {
            return;
        }

        String[] words = input.split(" ");

        if (inputEquals(words, new String[]{"go"}, new String[]{"north"})) {
            if (currentRoom.canMoveNorth()) {
                currentRoom = currentRoom.getNorth();
                io.println("You moved north.");
                describeCurrentRoom();
            } else {
                io.println("You can't move north.");
            }
            return;
        }

        if (inputEquals(words, new String[]{"go"}, new String[]{"east"})) {
            if (currentRoom.canMoveEast()) {
                currentRoom = currentRoom.getEast();
                io.println("You moved east.");
                describeCurrentRoom();
            } else {
                io.println("You can't move east.");
            }
            return;
        }

        if (inputEquals(words, new String[]{"go"}, new String[]{"south"})) {
            if (currentRoom.canMoveSouth()) {
                currentRoom = currentRoom.getSouth();
                io.println("You moved south.");
                describeCurrentRoom();
            } else {
                io.println("You can't move south.");
            }
            return;
        }

        if (inputEquals(words, new String[]{"go"}, new String[]{"west"})) {
            if (currentRoom.canMoveWest()) {
                currentRoom = currentRoom.getWest();
                io.println("You moved west.");
                describeCurrentRoom();
            } else {
                io.println("You can't move west.");
            }
            return;
        }

        if (inputEquals(words, new String[]{"pick"}, new String[]{"up"})) {
            if (words.length < 3) {
                io.println("Please specify which item to pick up.");
                return;
            }
            String itemName = words[2];
            for (int i = 3; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (currentRoom.containsItem(itemName)) {
                currentRoom.removeItem(itemName);
                hero.addItem(itemName);
                io.println("You picked up the " + itemName + ".");
                io.updateHero(hero);
                io.updateRoom(currentRoom);
            }
            if (hero.getCurrHPPotionAmount() == 3) {
                io.println("But your potion pouch is full you can't have anymore HP Potions");
            } else if (currentRoom.containsPot(pot)) {
                hero.addPotion(pot);
                currentRoom.removePot(pot);
                io.println("You now have " + hero.getCurrHPPotionAmount() + " potions in your potion pouch");
                io.updateHero(hero);
                io.updateRoom(currentRoom);
            }
            return;
        }

        if (inputEquals(words, new String[]{"use"})) {
            if (words.length < 2) {
                io.println("Please specify which weapon to use.");
                return;
            }
            String itemName = words[1];
            for (int i = 2; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (hero.getInventory().contains(itemName)) {
                hero.setWeaponInHand(allWeapons.get(itemName));
                hero.setPlayerDamage(allWeapons.get(itemName).getDamage());
                io.println("You now have " + itemName + " in your hands.");
                io.updateHero(hero);
            } else {
                io.println("That weapon isn't available");
            }
            return;
        }
        if (inputEquals(words, new String[]{"wear"})) {
            if (words.length < 2) {
                io.println("Please specify which armor to wear.");
                return;
            }
            String itemName = words[1];
            for (int i = 2; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (hero.containsItem(itemName)) {
                hero.setCurrentlyWornArmor(allArmors.get(itemName));
                hero.setArmor(allArmors.get(itemName).getGiveArmor());
                io.println("You now wore " + itemName);
                io.updateHero(hero);
            } else {
                io.println("That armor isn't available");
            }
            return;
        }

        if (inputEquals(words, new String[]{"drop"})) {
            if (words.length < 2) {
                io.println("Please specify which item to drop.");
                return;
            }
            String itemName = words[1];
            for (int i = 2; i < words.length; i++) {
                itemName += " " + words[i];
            }
            if (hero.containsItem(itemName)) {
                hero.removeItem(itemName);
                currentRoom.addItem(itemName);
                hero.setPlayerDamage(0);
                io.println("You dropped the " + itemName + ".");
                io.updateHero(hero);
                io.updateRoom(currentRoom);
            } else {
                io.println("That item isn't available");
            }
            return;
        }
        if (inputEquals(words, new String[]{"attack"})) {
            if (currentRoom.getEnemy() != null) {
                io.println("You engaged in a fight with a " + currentRoom.getEnemy().getEnemyName());
                io.println("You have " + hero.getHp() + " hp.");
                io.println("Your weapon deals " + hero.getPlayerDamage() + " damage");
                io.println(currentRoom.getEnemy().getEnemyName() + " can deal you " +
                        currentRoom.getEnemy().getEnemyDamage() + " damage");
                while (hero.getHp() > 0 && currentRoom.getEnemy().getEnemyHealth() > 0) {
                    io.println(currentRoom.getEnemy().getEnemyName() + " has " +
                            currentRoom.getEnemy().getEnemyHealth() + " hp.");
                    io.println(askUser);
                    io.println("Type 1 for attacking the enemy");
                    io.println("Type 2 for drinking a health potion");
                    int fightInput = io.readInt();
                    switch (fightInput) {
                        case 1 -> {
                            currentRoom.getEnemy().takeDamage(hero.getPlayerDamage());
                            hero.takeDamage(Math.max(0, currentRoom.getEnemy().getEnemyDamage() - hero.getCurrentlyWornArmor().getGiveArmor()));
                            io.println("Your armor protected you from " +
                                    hero.getCurrentlyWornArmor().getGiveArmor());
                            io.println("You have taken " + Math.max(0, currentRoom.getEnemy().getEnemyDamage() -
                                    hero.getCurrentlyWornArmor().getGiveArmor()) + " damage");
                            io.println("You gave enemy " + hero.getPlayerDamage() + " damage");
                            io.println("You have " + hero.getHp() + " hp.");
                            io.updateHero(hero);
                        }
                        case 2 -> drinkPot();
                        default -> io.println("Enter a valid command");
                    }
                }
                if (hero.getHp() <= 0) {
                    io.println("You died " + hero.getName());
                    io.println("Number of enemies you killed : " + totalDeadEnemyCount);
                    io.println("Number of people you saved : " + savedTownsPeopleCount);
                    highScore = this.getValues();
                    saveScore();
                    stopGame(false);
                } else if (currentRoom.getEnemy().getEnemyHealth() <= 0) {
                    io.println("You killed the " + currentRoom.getEnemy().getEnemyName());
                    currentRoom.addItem(currentRoom.getEnemy().getDropsItem());
                    io.println("Enemy dropped " + currentRoom.getEnemy().getDropsItem().getItemName());

                    io.println("You saved a human from the hands of the " +
                            currentRoom.getEnemy().getEnemyName());
                    enemyPotDropChance();
                    currentRoom.setEnemy(null);
                    levelHeroKillCount++;
                    totalDeadEnemyCount++;
                    savedTownsPeopleCount++;
                    if (rand.nextInt(100) < townsPeopleHealChance) {
                        io.println("The human you save gave you a food");
                        hero.setHp(hero.getHp() + townsPeopleHealAmount);
                        io.println("Food healed you for " + townsPeopleHealAmount + "hp.");
                        io.updateHero(hero);
                    } else {
                        io.println("The person you saved thanked you.");
                    }
                    io.updateRoom(currentRoom);
                }
            } else {
                io.println("There is nothing to fight in this room");
            }
            return;
        }

        if (inputEquals(words, new String[]{"inventory"})) {
            io.println("Inventory:");
            if (hero.getInventory().isEmpty()) {
                io.println(" - There is nothing in here.");
            } else {
                io.println(hero.getInventory().formatItems(" - %s"));
                io.println("** You have " + hero.getCurrHPPotionAmount() + " potions **");
            }
            return;
        }
        if (inputEquals(words, new String[]{"quit"})) {
            io.println("You decided to run away and didn't accomplished what you have came to dungeon for");
            io.println("You left all your items while escaping");
            io.println("You will never be known as an hero. Those who know you will call you a coward");
            stopGame(false);
            return;
        }
    }

    private static boolean inputEquals(String[] words, String[]... userInput) {
        if (words.length < userInput.length) {
            return false;
        }
        for (int i = 0; i < userInput.length; i++) {
            String word = words[i];
            String[] possibleWords = userInput[i];
            if (!equalsAny(word, possibleWords)) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsAny(String word, String[] possibleWords) {
        for (String possibility : possibleWords) {
            if (word.equalsIgnoreCase(possibility)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
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
        io.println(welcomeUser);
        io.println("You just got in the dungeon");
        io.println("You are in level 1");
        describeCurrentRoom();

        while (running) {
            io.println(askUser);
            String start = io.readLine();
            parseInput(start);

            if (!running) {
                break;
            }

            if (levelHeroKillCount == 3) {
                currentRoom = environment.goToNextLevel(io);
                levelHeroKillCount = 0;
                describeCurrentRoom();
            }

            if (environment.getCurrentLevel() == 17) {
                highScore = this.getValues();
                saveScore();
                stopGame(true);
            }
        }
    }

    private void stopGame(boolean heroWon) {
        running = false;
        io.onGameFinished(heroWon);
    }

    public void saveScore() {
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        try {
            writeFile = new FileWriter("highscore.txt");
            writer = new BufferedWriter(writeFile);
            writer.write(String.valueOf(highScore));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
