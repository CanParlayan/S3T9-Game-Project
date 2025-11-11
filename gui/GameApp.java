package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Game;
import objects.Hero;
import objects.Item;
import objects.Room;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameApp extends Application {
    private TextArea logArea;
    private TextField commandInput;
    private ListView<String> inventoryList;
    private ListView<String> roomItemsList;
    private Label heroNameLabel;
    private Label heroGenderLabel;
    private Label heroHpLabel;
    private Label heroArmorLabel;
    private Label heroDamageLabel;
    private Label heroWeaponLabel;
    private Label heroPotionsLabel;
    private Label roomDescriptionLabel;
    private Label enemyLabel;
    private final Map<String, String> roomItemCommandLookup = new HashMap<>();
    private final Map<String, String> heroItemCommandLookup = new HashMap<>();
    private GuiGameIO io;
    private final List<Button> commandButtons = new ArrayList<>();
    private volatile boolean gameFinished = false;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Dungeon Adventure");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);

        commandInput = new TextField();
        commandInput.setPromptText("Type a command (e.g. go north, attack, inventory)");
        commandInput.setOnAction(e -> {
            String text = commandInput.getText();
            if (text != null && !text.isBlank()) {
                sendCommand(text.trim(), true);
                commandInput.clear();
            }
        });

        inventoryList = new ListView<>();
        inventoryList.setPrefHeight(150);
        roomItemsList = new ListView<>();
        roomItemsList.setPrefHeight(150);

        heroNameLabel = new Label("Name: ");
        heroGenderLabel = new Label("Gender: ");
        heroHpLabel = new Label("HP: ");
        heroArmorLabel = new Label("Armor: ");
        heroDamageLabel = new Label("Damage: ");
        heroWeaponLabel = new Label("Weapon: ");
        heroPotionsLabel = new Label("Potions: 0");
        roomDescriptionLabel = new Label("Room: ");
        roomDescriptionLabel.setWrapText(true);
        enemyLabel = new Label("Enemy: None");
        enemyLabel.setWrapText(true);

        VBox heroBox = new VBox(5, heroNameLabel, heroGenderLabel, heroHpLabel,
                heroArmorLabel, heroDamageLabel, heroWeaponLabel, heroPotionsLabel);
        heroBox.setPadding(new Insets(10));
        heroBox.setStyle("-fx-border-color: #444; -fx-border-width: 1; -fx-border-radius: 4; -fx-padding: 10;");

        VBox inventoryBox = new VBox(5,
                new Label("Inventory"),
                inventoryList);
        inventoryBox.setPadding(new Insets(10));
        inventoryBox.setStyle("-fx-border-color: #444; -fx-border-width: 1; -fx-border-radius: 4; -fx-padding: 10;");

        VBox roomBox = new VBox(5,
                roomDescriptionLabel,
                enemyLabel,
                new Label("Items in Room"),
                roomItemsList);
        roomBox.setPadding(new Insets(10));
        roomBox.setStyle("-fx-border-color: #444; -fx-border-width: 1; -fx-border-radius: 4; -fx-padding: 10;");

        GridPane movementGrid = new GridPane();
        movementGrid.setHgap(5);
        movementGrid.setVgap(5);
        movementGrid.setAlignment(Pos.CENTER);
        Button northButton = createCommandButton("North", "go north");
        Button southButton = createCommandButton("South", "go south");
        Button eastButton = createCommandButton("East", "go east");
        Button westButton = createCommandButton("West", "go west");
        movementGrid.add(northButton, 1, 0);
        movementGrid.add(westButton, 0, 1);
        movementGrid.add(eastButton, 2, 1);
        movementGrid.add(southButton, 1, 2);

        Button attackButton = createCommandButton("Attack", "attack");
        Button inventoryButton = createCommandButton("Show Inventory", "inventory");
        Button quitButton = createCommandButton("Quit", "quit");
        Button fightAttackButton = createCommandButton("Fight: Attack", "1");
        Button fightPotionButton = createCommandButton("Fight: Potion", "2");

        Button pickUpButton = new Button("Pick Up Selected");
        pickUpButton.setOnAction(e -> {
            String selected = roomItemsList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String commandKey = roomItemCommandLookup.get(selected);
                if (commandKey != null) {
                    sendCommand("pick up " + commandKey, true);
                } else {
                    sendCommand("pick up " + selected, true);
                }
            }
        });
        commandButtons.add(pickUpButton);

        Button useSelectedButton = new Button("Use Selected");
        useSelectedButton.setOnAction(e -> {
            String selected = inventoryList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String commandKey = heroItemCommandLookup.get(selected);
                if (commandKey != null) {
                    sendCommand("use " + commandKey, true);
                }
            }
        });
        commandButtons.add(useSelectedButton);

        Button wearSelectedButton = new Button("Wear Selected");
        wearSelectedButton.setOnAction(e -> {
            String selected = inventoryList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String commandKey = heroItemCommandLookup.get(selected);
                if (commandKey != null) {
                    sendCommand("wear " + commandKey, true);
                }
            }
        });
        commandButtons.add(wearSelectedButton);

        Button dropSelectedButton = new Button("Drop Selected");
        dropSelectedButton.setOnAction(e -> {
            String selected = inventoryList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String commandKey = heroItemCommandLookup.get(selected);
                if (commandKey != null) {
                    sendCommand("drop " + commandKey, true);
                }
            }
        });
        commandButtons.add(dropSelectedButton);

        HBox inventoryActions = new HBox(5, pickUpButton, useSelectedButton, wearSelectedButton, dropSelectedButton);
        inventoryActions.setAlignment(Pos.CENTER);

        VBox actionBox = new VBox(10,
                new Label("Movement"),
                movementGrid,
                new Label("Actions"),
                attackButton,
                inventoryButton,
                fightAttackButton,
                fightPotionButton,
                quitButton);
        actionBox.setAlignment(Pos.TOP_CENTER);
        actionBox.setPadding(new Insets(10));


        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setCenter(logArea);
        VBox leftPane = new VBox(10, heroBox, inventoryBox, inventoryActions);
        root.setLeft(leftPane);
        VBox rightPane = new VBox(10, roomBox, actionBox);
        root.setRight(rightPane);
        root.setBottom(new VBox(5, new Label("Command"), commandInput));

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.show();

        io = new GuiGameIO(this::appendMessage, this::updateHeroStats, this::updateRoomDetails, this::onGameFinished);
        Game game = new Game(io);
        Thread gameThread = new Thread(game::run);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private Button createCommandButton(String text, String command) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> sendCommand(command, true));
        commandButtons.add(button);
        return button;
    }

    private void sendCommand(String command, boolean echo) {
        if (gameFinished || io == null) {
            return;
        }
        if (echo) {
            appendMessage("> " + command);
        }
        io.submitCommand(command);
    }

    private void appendMessage(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        logArea.appendText(message + "\n");
    }

    private void updateHeroStats(Hero hero) {
        if (hero == null) {
            return;
        }
        heroNameLabel.setText("Name: " + nullToUnknown(hero.getName()));
        heroGenderLabel.setText("Gender: " + nullToUnknown(hero.getGender()));
        heroHpLabel.setText("HP: " + hero.getHp());
        heroArmorLabel.setText("Armor: " + hero.getArmor());
        heroDamageLabel.setText("Damage: " + hero.getPlayerDamage());
        heroWeaponLabel.setText("Weapon: " + (hero.getWeaponInHand() != null ? hero.getWeaponInHand().getItemName() : "None"));
        heroPotionsLabel.setText("Potions: " + hero.getCurrHPPotionAmount());

        heroItemCommandLookup.clear();
        Set<Item> items = hero.getInventory().getItems();
        List<String> display = items.stream()
                .sorted(Comparator.comparing(Item::getItemName))
                .map(item -> buildDisplayLabel(item, heroItemCommandLookup))
                .collect(Collectors.toList());
        inventoryList.getItems().setAll(display);
    }

    private void updateRoomDetails(Room room) {
        if (room == null) {
            roomDescriptionLabel.setText("Room: ");
            enemyLabel.setText("Enemy: None");
            roomItemsList.getItems().clear();
            return;
        }
        roomDescriptionLabel.setText("Room: " + room.getDescription());
        if (room.getEnemy() != null) {
            enemyLabel.setText(String.format("Enemy: %s (HP: %d, Damage: %d)",
                    room.getEnemy().getEnemyName(),
                    room.getEnemy().getEnemyHealth(),
                    room.getEnemy().getEnemyDamage()));
        } else {
            enemyLabel.setText("Enemy: None");
        }
        roomItemCommandLookup.clear();
        List<String> roomItems = room.getItems().stream()
                .sorted(Comparator.comparing(Item::getItemName))
                .map(item -> buildDisplayLabel(item, roomItemCommandLookup))
                .collect(Collectors.toList());
        roomItemsList.getItems().setAll(roomItems);
    }

    private String buildDisplayLabel(Item item, Map<String, String> lookup) {
        String commandKey = findCommandKey(item);
        String display = item.getItemName();
        if (commandKey != null && !commandKey.isBlank()) {
            display = display + " (" + commandKey + ")";
            lookup.put(display, commandKey);
        } else {
            lookup.put(display, display);
        }
        return display;
    }

    private String findCommandKey(Item item) {
        for (Map.Entry<String, Item> entry : Game.allItems.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }
        return item.getItemName().toLowerCase().replaceAll("\\s+", "");
    }

    private void onGameFinished(boolean heroWon) {
        gameFinished = true;
        commandButtons.forEach(button -> button.setDisable(true));
        commandInput.setDisable(true);
        appendMessage(heroWon ? "You cleared the dungeon!" : "Game over. The dungeon remains dangerous.");
    }

    private String nullToUnknown(String value) {
        return value == null || value.isBlank() ? "Unknown" : value;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
