package objects;
import enemies.*;
import rooms.*;
import java.util.HashMap;
import java.util.Map;
public class Environment {

    int currentLevel = 0;
    Map<String, Room> level1 = new HashMap<>();
    Map<String, Room> level2 = new HashMap<>();
    Map<String, Room> level3 = new HashMap<>();
    Map<String, Room> level4 = new HashMap<>();
    Map<String, Room> level5 = new HashMap<>();
    Map<String, Room> level6 = new HashMap<>();
    Map<String, Room> level7 = new HashMap<>();
    Map<String, Room> level8 = new HashMap<>();
    Map<String, Room> level9 = new HashMap<>();
    Map<String, Room> level10 = new HashMap<>();
    Map<String, Room> level11 = new HashMap<>();
    Map<String, Room> level12 = new HashMap<>();
    Map<String, Room> level13 = new HashMap<>();
    Map<String, Room> level14 = new HashMap<>();
    Map<String, Room> level15 = new HashMap<>();
    Map<String, Room> level16 = new HashMap<>();


    public int getCurrentLevel() {
        return this.currentLevel;
    }


    void initializeLevel1(){
        Room blankRoom = new BlankRoom();
        Room zombieRoom = new ZombieRoom(new Zombie(Game.pot));
        Room zombieRoom2 = new ZombieRoom(new Zombie(Game.pot));
        Room skeletonRoom = new SkeletonRoom(new Skeleton(Game.dragonSword));
        Room blankRoom2 = new BlankRoom();
        blankRoom.setNorth(zombieRoom);
        blankRoom.setEast(blankRoom2);
        blankRoom.setWest(skeletonRoom);
        blankRoom.setSouth(zombieRoom2);
        blankRoom2.setWest(blankRoom);
        skeletonRoom.setEast(blankRoom);
        zombieRoom2.setNorth(blankRoom);
        zombieRoom.setSouth(blankRoom);
        level1.put("blankroom", blankRoom);
        level1.put("blankroom2", blankRoom2);
        level1.put("skeletonroom", skeletonRoom);
        level1.put("zombieroom", zombieRoom);
        level1.put("zombieroom2", zombieRoom2);
    }

    void initializeLevel2() {
        Room trollRoom = new TrollRoom(new Troll(Game.smallAxe));
        Room blankRoom = new BlankRoom();
        Room zombieRoom = new ZombieRoom(new Zombie(Game.longSword));
        Room zombieRoom2 = new ZombieRoom(new Zombie(Game.pot));
        Room blankRoom2 = new BlankRoom();
        level2.put("blankroom", blankRoom);
        level2.put("trollroom", trollRoom);
        level2.put("zombieroom", zombieRoom);
        level2.put("zombieroom2", zombieRoom2);

        blankRoom.setNorth(trollRoom);
        trollRoom.setSouth(blankRoom);
        blankRoom.setWest(zombieRoom);
        zombieRoom.setEast(blankRoom);
        blankRoom.setEast(zombieRoom2);
        zombieRoom2.setWest(blankRoom);
        blankRoom.setSouth(blankRoom2);
        blankRoom2.setNorth(blankRoom);


    }
    void initializeLevel3(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room vampireRoom = new VampireRoom(new Vampire(Game.smallAxe));
        Room skeletonRoom = new SkeletonRoom(new Skeleton(Game.shortSword));
        Room wolfRoom = new WolfRoom(new Wolf(Game.broadAxe));

        level3.put("blankroom",blankRoom);
        level3.put("blankroom2",blankRoom2);
        level3.put("vampireroom",vampireRoom);
        level3.put("wolfroom",wolfRoom);

        blankRoom.setNorth(vampireRoom);
        vampireRoom.setSouth(blankRoom);
        blankRoom.setWest(skeletonRoom);
        skeletonRoom.setEast(blankRoom);
        blankRoom.setEast(blankRoom2);
        blankRoom2.setWest(blankRoom);
        blankRoom.setSouth(wolfRoom);
        wolfRoom.setNorth(blankRoom);

    }
    void initializeLevel4(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room spiderRoom = new SpiderRoom(new Spider(Game.shortbow));
        Room wizardRoom = new WizardRoom(new Wizard(Game.shortSword));
        Room trollRoom = new TrollRoom(new Troll(Game.leatherArmor));

        level4.put("blankroom",blankRoom);
        level4.put("blankroom2", blankRoom2);
        level4.put("spiderroom",spiderRoom);
        level4.put("trollroom",trollRoom);
        level4.put("wizardroom",wizardRoom);

        blankRoom.setNorth(trollRoom);
        trollRoom.setSouth(blankRoom);
        blankRoom.setWest(wizardRoom);
        wizardRoom.setEast(blankRoom);
        blankRoom.setSouth(blankRoom2);
        blankRoom2.setNorth(blankRoom);
        blankRoom.setEast(spiderRoom);
        spiderRoom.setWest(blankRoom);
    }
    void initializeLevel5(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room orcRoom = new SpiderRoom(new Orc(Game.shortbow));
        Room beastRoom = new BeastRoom(new Beast(Game.longbow));
        Room elfRoom = new ElfRoom(new Elf(Game.crossbow));

        level5.put("blankroom",blankRoom);
        level5.put("blankroom2", blankRoom2);
        level5.put("orcroom",orcRoom);
        level5.put("beastroom",beastRoom);
        level5.put("elfroom",elfRoom);

        blankRoom.setNorth(orcRoom);
        orcRoom.setSouth(blankRoom);
        blankRoom.setWest(blankRoom2);
        blankRoom2.setEast(blankRoom);
        blankRoom.setSouth(beastRoom);
        beastRoom.setNorth(blankRoom);
        blankRoom.setEast(elfRoom);
        elfRoom.setWest(blankRoom);
    }
    void initializeLevel6(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room assassinRoom = new AssassinRoom(new Assassin(Game.longSword));
        Room ghostRoom = new GhostRoom(new Ghost(Game.dagger));
        Room goblinRoom = new GoblinRoom(new Goblin(Game.crossbow));

        level6.put("blankroom",blankRoom);
        level6.put("blankroom2", blankRoom2);
        level6.put("assassinroom",assassinRoom);
        level6.put("ghostroom",ghostRoom);
        level6.put("goblinroom",goblinRoom);

        blankRoom.setNorth(blankRoom2);
        blankRoom2.setSouth(blankRoom);
        blankRoom.setWest(assassinRoom);
        assassinRoom.setEast(blankRoom);
        blankRoom.setSouth(goblinRoom);
        goblinRoom.setNorth(blankRoom);
        blankRoom.setEast(ghostRoom);
        ghostRoom.setWest(blankRoom);
    }
    void initializeLevel7(){
        Room blankRoom = new BlankRoom();
        Room skeletonRoom = new SkeletonRoom(new Skeleton(Game.shortSword));
        Room blankRoom2 = new BlankRoom();
        Room giantRoom = new GiantRoom(new Giant(Game.smallAxe));
        Room vampireRoom = new VampireRoom(new Vampire(Game.smallAxe));

        level7.put("blankroom",blankRoom);
        level7.put("blankroom2", blankRoom2);
        level7.put("skeletonroom",skeletonRoom);
        level7.put("giantroom",giantRoom);
        level7.put("vampireroom",vampireRoom);

        blankRoom.setNorth(skeletonRoom);
        skeletonRoom.setSouth(blankRoom);
        blankRoom.setWest(giantRoom);
        giantRoom.setEast(blankRoom);
        blankRoom.setSouth(blankRoom2);
        blankRoom2.setNorth(blankRoom);
        blankRoom.setEast(vampireRoom);
        vampireRoom.setWest(blankRoom);
    }
    void initializeLevel8(){
        Room blankRoom = new BlankRoom();
        Room assassinRoom = new AssassinRoom(new Assassin(Game.longSword));
        Room trollRoom = new TrollRoom(new Troll(Game.leatherArmor));
        Room blankRoom2 = new BlankRoom();
        Room wolfRoom = new WolfRoom(new Wolf(Game.broadAxe));

        level8.put("blankroom",blankRoom);
        level8.put("assassinroom", assassinRoom);
        level8.put("trollroom",trollRoom);
        level8.put("blankroom2",blankRoom2);
        level8.put("wolfroom",wolfRoom);

        blankRoom.setNorth(assassinRoom);
        assassinRoom.setSouth(blankRoom);
        blankRoom.setWest(trollRoom);
        trollRoom.setEast(blankRoom);
        blankRoom.setSouth(wolfRoom);
        wolfRoom.setNorth(blankRoom);
        blankRoom.setEast(blankRoom2);
        blankRoom2.setWest(blankRoom);
    }
    void initializeLevel9(){
        Room blankRoom = new BlankRoom();
        Room goblinRoom = new GoblinRoom(new Goblin(Game.crossbow));
        Room spiderRoom = new SpiderRoom(new Spider(Game.shortbow));
        Room blankRoom2 = new BlankRoom();
        Room beastRoom = new BeastRoom(new Beast(Game.longbow));

        level9.put("blankroom",blankRoom);
        level9.put("goblinroom", goblinRoom);
        level9.put("spiderroom",spiderRoom);
        level9.put("beastroom",beastRoom);
        level9.put("blankroom2",blankRoom2);

        blankRoom.setNorth(goblinRoom);
        goblinRoom.setSouth(blankRoom);
        blankRoom.setWest(spiderRoom);
        spiderRoom.setEast(blankRoom);
        blankRoom.setSouth(beastRoom);
        beastRoom.setNorth(blankRoom);
        blankRoom.setEast(blankRoom2);
        blankRoom2.setWest(blankRoom);
    }
    void initializeLevel10(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room orcRoom = new OrcRoom(new Orc(Game.shortbow));
        Room wizardRoom = new WizardRoom(new Wizard(Game.shortSword));
        Room giantRoom = new GiantRoom(new Giant(Game.dagger));

        level10.put("blankroom",blankRoom);
        level10.put("blankroom2", blankRoom2);
        level10.put("orcroom",orcRoom);
        level10.put("wizardroom",wizardRoom);
        level10.put("giantroom",giantRoom);

        blankRoom.setNorth(blankRoom2);
        blankRoom2.setSouth(blankRoom);
        blankRoom.setWest(orcRoom);
        orcRoom.setEast(blankRoom);
        blankRoom.setSouth(wizardRoom);
        wizardRoom.setNorth(blankRoom);
        blankRoom.setEast(giantRoom);
        giantRoom.setWest(blankRoom);
    }
    void initializeLevel11(){
        Room blankRoom = new BlankRoom();
        Room giantRoom = new GiantRoom(new Giant(Game.smallAxe));
        Room elfRoom = new ElfRoom(new Elf(Game.crossbow));
        Room blankRoom2 = new BlankRoom();
        Room skeletonRoom = new SkeletonRoom(new Skeleton(Game.shortSword));

        level11.put("blankroom",blankRoom);
        level11.put("giantroom", giantRoom);
        level11.put("elfroom",elfRoom);
        level11.put("blankroom2",blankRoom2);
        level11.put("skeletonroom",skeletonRoom);

        blankRoom.setNorth(giantRoom);
        giantRoom.setSouth(blankRoom);
        blankRoom.setWest(elfRoom);
        elfRoom.setEast(blankRoom);
        blankRoom.setSouth(blankRoom2);
        blankRoom2.setNorth(blankRoom);
        blankRoom.setEast(skeletonRoom);
        skeletonRoom.setWest(blankRoom);
    }
    void initializeLevel12(){
        Room blankRoom = new BlankRoom();
        Room beastRoom = new BeastRoom(new Beast(Game.longbow));
        Room orcRoom = new OrcRoom(new Orc(Game.shortbow));
        Room blankRoom2 = new BlankRoom();
        Room spiderRoom = new SpiderRoom(new Spider(Game.shortbow));

        level12.put("blankroom",blankRoom);
        level12.put("beastroom", beastRoom);
        level12.put("orcroom",orcRoom);
        level12.put("blankroom2",blankRoom2);
        level12.put("spiderroom",spiderRoom);

        blankRoom.setNorth(beastRoom);
        beastRoom.setSouth(blankRoom);
        blankRoom.setWest(orcRoom);
        orcRoom.setEast(blankRoom);
        blankRoom.setSouth(blankRoom2);
        blankRoom2.setNorth(blankRoom);
        blankRoom.setEast(spiderRoom);
        spiderRoom.setWest(blankRoom);
    }
    void initializeLevel13(){
        Room blankRoom = new BlankRoom();
        Room wizardRoom = new WizardRoom(new Wizard(Game.shortSword));
        Room blankRoom2 = new BlankRoom();
        Room elfRoom = new ElfRoom(new Elf(Game.crossbow));
        Room assassinRoom = new AssassinRoom(new Assassin(Game.longSword));

        level13.put("blankroom",blankRoom);
        level13.put("wizardroom", wizardRoom);
        level13.put("blankroom2",blankRoom2);
        level13.put("elfroom",elfRoom);
        level13.put("assassinroom",assassinRoom);

        blankRoom.setNorth(wizardRoom);
        wizardRoom.setSouth(blankRoom);
        blankRoom.setWest(blankRoom2);
        blankRoom2.setEast(blankRoom);
        blankRoom.setSouth(elfRoom);
        elfRoom.setNorth(blankRoom);
        blankRoom.setEast(assassinRoom);
        assassinRoom.setWest(blankRoom);
    }
    void initializeLevel14(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room goblinRoom = new GoblinRoom(new Goblin(Game.crossbow));
        Room wizardRoom = new WizardRoom(new Wizard(Game.shortSword));
        Room orcRoom = new OrcRoom(new Orc(Game.shortbow));

        level14.put("blankroom",blankRoom);
        level14.put("blankroom2",blankRoom2);
        level14.put("goblinroom",goblinRoom);
        level14.put("wizardroom",wizardRoom);
        level14.put("orcroom",orcRoom);

        blankRoom.setNorth(blankRoom2);
        blankRoom2.setSouth(blankRoom);
        blankRoom.setWest(goblinRoom);
        goblinRoom.setEast(blankRoom);
        blankRoom.setSouth(wizardRoom);
        wizardRoom.setNorth(blankRoom);
        blankRoom.setEast(orcRoom);
        orcRoom.setWest(blankRoom);
    }
    void initializeLevel15(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room dragonRoom = new DragonRoom(new Dragon(Game.dragonSword));
        Room assassinRoom = new AssassinRoom(new Assassin(Game.longSword));
        Room beastRoom = new BeastRoom(new Beast(Game.longbow));

        level15.put("blankroom",blankRoom);
        level15.put("blankroom2", blankRoom2);
        level15.put("dragonroom",dragonRoom);
        level15.put("assassinroom",assassinRoom);
        level15.put("beastroom",beastRoom);

        blankRoom.setNorth(blankRoom2);
        blankRoom2.setSouth(blankRoom);
        blankRoom.setWest(dragonRoom);
        dragonRoom.setEast(blankRoom);
        blankRoom.setSouth(assassinRoom);
        assassinRoom.setNorth(blankRoom);
        blankRoom.setEast(beastRoom);
        beastRoom.setWest(blankRoom);
    }
    void initializeLevel16(){
        Room blankRoom = new BlankRoom();
        Room blankRoom2 = new BlankRoom();
        Room dragonRoom1 = new DragonRoom(new Dragon(Game.dragonSword));
        Room dragonRoom2 = new DragonRoom(new Dragon(Game.dragonSword));
        Room dragonRoom3 = new DragonRoom(new Dragon(Game.dragonSword));

        level16.put("blankroom", blankRoom);
        level16.put("blankroom2",blankRoom2);
        level16.put("dragonroom1",dragonRoom1);
        level16.put("dragonroom2",dragonRoom2);
        level16.put("dragonroom3",dragonRoom3);

        blankRoom.setNorth(dragonRoom1);
        dragonRoom1.setSouth(blankRoom);
        blankRoom.setWest(dragonRoom2);
        dragonRoom2.setEast(blankRoom);
        blankRoom.setSouth(dragonRoom3);
        dragonRoom3.setNorth(blankRoom);
        blankRoom.setEast(blankRoom2);
        blankRoom2.setWest(blankRoom);

    }




    Room getLevel1StartingRoom() {
        return level1.get("blankroom");
    }
    Room getLevel2StartingRoom() {
        return level2.get("blankroom");
    }
    Room getLevel3StartingRoom(){return level3.get("blankroom");}
    Room getLevel4StartingRoom(){return level4.get("blankroom");}
    Room getLevel5StartingRoom(){return level5.get("blankroom");}
    Room getLevel6StartingRoom(){return level6.get("blankroom");}
    Room getLevel7StartingRoom(){return level7.get("blankroom");}
    Room getLevel8StartingRoom(){return level8.get("blankroom");}
    Room getLevel9StartingRoom(){return level9.get("blankroom");}
    Room getLevel10StartingRoom(){return level10.get("blankroom");}
    Room getLevel11StartingRoom(){return level11.get("blankroom");}
    Room getLevel12StartingRoom(){return level12.get("blankroom");}
    Room getLevel13StartingRoom(){return level13.get("blankroom");}
    Room getLevel14StartingRoom(){return level14.get("blankroom");}
    Room getLevel15StartingRoom(){return level15.get("blankroom");}
    Room getLevel16StartingRoom(){return level16.get("blankroom");}





    Room goToNextLevel() {
        currentLevel += 1;
        if (currentLevel == 2) {
            System.out.println("You are in level 2");
            return getLevel2StartingRoom();
        }else if (currentLevel == 3){
            System.out.println("You are in level 3");
            return getLevel3StartingRoom();
        }else if (currentLevel == 4){
            System.out.println("You are in level 4");
            return getLevel4StartingRoom();
        }else if (currentLevel == 5){
            System.out.println("You are in level 5");
            return getLevel5StartingRoom();
        }else if (currentLevel == 6){
            System.out.println("You are in level 6");
            return getLevel6StartingRoom();
        }else if (currentLevel == 7){
            System.out.println("You are in level 7");
            return getLevel7StartingRoom();
        }else if (currentLevel == 8){
            System.out.println("You are in level 8");
            return getLevel8StartingRoom();
        }else if (currentLevel == 9){
            System.out.println("You are in level 9");
            return getLevel9StartingRoom();
        }else if (currentLevel == 10){
            System.out.println("You are in level 10");
            return getLevel10StartingRoom();
        }else if (currentLevel == 11){
            System.out.println("You are in level 11");
            return getLevel11StartingRoom();
        }else if (currentLevel == 12){
            System.out.println("You are in level 12");
            return getLevel12StartingRoom();
        }else if (currentLevel == 13){
            System.out.println("You are in level 13");
            return getLevel13StartingRoom();
        }else if (currentLevel == 14){
            System.out.println("You are in level 14");
            return getLevel14StartingRoom();
        }else if (currentLevel == 15){
            System.out.println("You are in level 15");
            return getLevel15StartingRoom();
        }else if (currentLevel == 16){
            System.out.println("You are in level 16");
            return getLevel16StartingRoom();
        }else if (currentLevel == 17){
            System.out.println("You have reached the end of the dungeon and defeated all the enemies. Congratulations");
        }
        System.out.println("You are in level 1");
        return getLevel1StartingRoom();
    }}

