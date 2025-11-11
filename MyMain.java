import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gdx.DungeonGame;
import objects.Game;
import objects.io.ConsoleGameIO;

public class MyMain {

    public static void main(String[] args) {
        if (args.length > 0 && "--console".equalsIgnoreCase(args[0])) {
            Game game = new Game(new ConsoleGameIO());
            game.run();
        } else {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Dungeon Adventure");
            config.useVsync(true);
            config.setWindowedMode(1100, 700);
            new Lwjgl3Application(new DungeonGame(), config);
        }
    }
}
