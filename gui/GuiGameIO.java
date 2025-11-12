package gui;

import javafx.application.Platform;
import objects.Hero;
import objects.Room;
import objects.io.GameIO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class GuiGameIO implements GameIO {
    private final BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private final Consumer<String> messageConsumer;
    private final Consumer<Hero> heroConsumer;
    private final Consumer<Room> roomConsumer;
    private final Consumer<Boolean> finishedConsumer;

    public GuiGameIO(Consumer<String> messageConsumer,
                     Consumer<Hero> heroConsumer,
                     Consumer<Room> roomConsumer,
                     Consumer<Boolean> finishedConsumer) {
        this.messageConsumer = messageConsumer;
        this.heroConsumer = heroConsumer;
        this.roomConsumer = roomConsumer;
        this.finishedConsumer = finishedConsumer;
    }

    public void submitCommand(String command) {
        if (command == null) {
            return;
        }
        inputQueue.offer(command);
    }

    @Override
    public void println(String message) {
        if (messageConsumer == null) {
            return;
        }
        Platform.runLater(() -> messageConsumer.accept(message));
    }

    @Override
    public String readLine() {
        try {
            return inputQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    @Override
    public int readInt() {
        while (true) {
            String value = readLine();
            if (value == null) {
                continue;
            }
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                println("Please enter a valid number.");
            }
        }
    }

    @Override
    public void updateHero(Hero hero) {
        if (heroConsumer == null) {
            return;
        }
        Platform.runLater(() -> heroConsumer.accept(hero));
    }

    @Override
    public void updateRoom(Room room) {
        if (roomConsumer == null) {
            return;
        }
        Platform.runLater(() -> roomConsumer.accept(room));
    }

    @Override
    public void onGameFinished(boolean heroWon) {
        if (finishedConsumer == null) {
            return;
        }
        Platform.runLater(() -> finishedConsumer.accept(heroWon));
    }
}
