package objects.io;

import objects.Hero;
import objects.Room;

public interface GameIO {
    void println(String message);

    String readLine();

    int readInt();

    default void updateHero(Hero hero) {
    }

    default void updateRoom(Room room) {
    }

    default void onGameFinished(boolean heroWon) {
    }
}
