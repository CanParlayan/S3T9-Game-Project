package objects;

public class Enemy {
    private int EnemyHealth;
    private int enemyDamage;
    private Item dropsItem = null;
    private String enemyName;

    public Enemy(String enemyName, int EnemyHealth, int enemyDamage, Item dropsItem) {
        this.enemyName = enemyName;
        this.EnemyHealth = EnemyHealth;
        this.enemyDamage = enemyDamage;
        this.dropsItem = dropsItem;
    }

    public void takeDamage(int damage){
        this.EnemyHealth -=damage;
    }
    public void setEnemyHealth(int enemyHealth) {
        EnemyHealth = enemyHealth;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getEnemyHealth() {
        return EnemyHealth;
    }

    public void setMaxEnemyHealth(int EnemyHealth) {
        this.EnemyHealth = EnemyHealth;
    }

    public int getEnemyDamage() {
        return enemyDamage;
    }

    public void setEnemyDamage(int enemyAttackDamage) {
        this.enemyDamage = enemyDamage;
    }

    public Item getDropsItem() {
        return dropsItem;
    }

    public void setDropsItem(Item dropsItem) {
        this.dropsItem = dropsItem;
    }


}
