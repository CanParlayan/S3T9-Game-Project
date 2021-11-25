package items;

import objects.Item;

public class Crossbow extends Item {
    public Crossbow() {
        super("Crossbow", "An arrow weapon with a mechanical bow. An excellent choice for long distances \n Damage: 50 \n" +
                        "Range: 10 \nThis item will give you 25 points",
                500, 30.0, 3, 10);
    }
}
