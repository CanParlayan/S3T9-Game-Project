package items;

import objects.Item;

public class Shortbow extends Item {
    public Shortbow() {
        super("Shortbow", "A shortbow does not provide long distance but, it has perfect damage. \n Damage: 20 \n Range: 10 \n " +
                        "This item will give you 20 points. ",
                250, 20.0, 3, 10);
    }
}
