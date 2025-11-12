import gui.GameApp;
import objects.Game;
import objects.io.ConsoleGameIO;

public class MyMain {

    public static void main(String[] args) {
        if (args.length > 0 && "--console".equalsIgnoreCase(args[0])) {
            Game game = new Game(new ConsoleGameIO());
            game.run();
        } else {
            GameApp.launch(GameApp.class, args);
        }
    }
}
