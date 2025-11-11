package objects.io;

import java.util.Scanner;

public class ConsoleGameIO implements GameIO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                String skipped = scanner.nextLine();
                System.out.println("Please enter a number: " + skipped);
            }
        }
    }

    @Override
    public void onGameFinished(boolean heroWon) {
        System.exit(heroWon ? 0 : 1);
    }
}
