package gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import objects.Game;
import objects.Hero;
import objects.Item;
import objects.Room;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DungeonGame extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private TextArea logArea;
    private TextField commandInput;
    private List<String> inventoryList;
    private List<String> roomItemsList;
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
    private final java.util.List<Actor> commandActors = new java.util.ArrayList<>();
    private LibGdxGameIO io;
    private volatile boolean gameFinished;
    private Thread gameThread;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = createBasicSkin();

        buildUi();

        io = new LibGdxGameIO(this::appendMessage, this::updateHeroStats, this::updateRoomDetails, this::onGameFinished);
        Game game = new Game(io);
        gameThread = new Thread(game::run, "dungeon-game-thread");
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private void buildUi() {
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(6);
        stage.addActor(root);

        // Log area in the center
        logArea = new TextArea("", skin);
        logArea.setDisabled(true);
        logArea.setPrefRows(25);
        ScrollPane logScroll = new ScrollPane(logArea, skin);
        logScroll.setFadeScrollBars(false);

        // Hero detail panel
        heroNameLabel = new Label("Name: ", skin);
        heroGenderLabel = new Label("Gender: ", skin);
        heroHpLabel = new Label("HP: ", skin);
        heroArmorLabel = new Label("Armor: ", skin);
        heroDamageLabel = new Label("Damage: ", skin);
        heroWeaponLabel = new Label("Weapon: ", skin);
        heroPotionsLabel = new Label("Potions: 0", skin);

        Table heroTable = new Table(skin);
        heroTable.defaults().pad(2).left();
        heroTable.add(heroNameLabel).row();
        heroTable.add(heroGenderLabel).row();
        heroTable.add(heroHpLabel).row();
        heroTable.add(heroArmorLabel).row();
        heroTable.add(heroDamageLabel).row();
        heroTable.add(heroWeaponLabel).row();
        heroTable.add(heroPotionsLabel).row();
        heroTable.pad(8);

        // Inventory list
        inventoryList = new List<>(skin);
        ScrollPane inventoryScroll = new ScrollPane(inventoryList, skin);
        inventoryScroll.setFadeScrollBars(false);
        Table inventoryTable = new Table(skin);
        inventoryTable.defaults().pad(2);
        inventoryTable.add(new Label("Inventory", skin)).padBottom(4).row();
        inventoryTable.add(inventoryScroll).width(260).height(160);
        inventoryTable.pad(8);

        // Room info
        roomDescriptionLabel = new Label("Room: ", skin);
        roomDescriptionLabel.setWrap(true);
        enemyLabel = new Label("Enemy: None", skin);
        enemyLabel.setWrap(true);
        roomItemsList = new List<>(skin);
        ScrollPane roomScroll = new ScrollPane(roomItemsList, skin);
        roomScroll.setFadeScrollBars(false);
        Table roomTable = new Table(skin);
        roomTable.defaults().pad(2).left();
        roomTable.add(roomDescriptionLabel).width(280).row();
        roomTable.add(enemyLabel).width(280).padBottom(4).row();
        roomTable.add(new Label("Items in Room", skin)).row();
        roomTable.add(roomScroll).width(280).height(160);
        roomTable.pad(8);

        // Action buttons
        Table movementTable = new Table();
        movementTable.defaults().pad(2).width(90);
        TextButton northButton = createCommandButton("North", "go north");
        TextButton southButton = createCommandButton("South", "go south");
        TextButton eastButton = createCommandButton("East", "go east");
        TextButton westButton = createCommandButton("West", "go west");
        movementTable.add().width(90);
        movementTable.add(northButton);
        movementTable.add().row();
        movementTable.add(westButton);
        movementTable.add().width(90);
        movementTable.add(eastButton).row();
        movementTable.add().width(90);
        movementTable.add(southButton);
        movementTable.add().width(90);

        TextButton attackButton = createCommandButton("Attack", "attack");
        TextButton inventoryButton = createCommandButton("Show Inventory", "inventory");
        TextButton quitButton = createCommandButton("Quit", "quit");
        TextButton fightAttackButton = createCommandButton("Fight: Attack", "1");
        TextButton fightPotionButton = createCommandButton("Fight: Potion", "2");

        TextButton pickUpButton = new TextButton("Pick Up Selected", skin);
        pickUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = roomItemsList.getSelected();
                if (selected != null) {
                    String commandKey = roomItemCommandLookup.get(selected);
                    if (commandKey == null) {
                        commandKey = selected;
                    }
                    sendCommand("pick up " + commandKey, true);
                }
            }
        });
        commandActors.add(pickUpButton);

        TextButton useSelectedButton = new TextButton("Use Selected", skin);
        useSelectedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = inventoryList.getSelected();
                if (selected != null) {
                    String commandKey = heroItemCommandLookup.get(selected);
                    if (commandKey != null) {
                        sendCommand("use " + commandKey, true);
                    }
                }
            }
        });
        commandActors.add(useSelectedButton);

        TextButton wearSelectedButton = new TextButton("Wear Selected", skin);
        wearSelectedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = inventoryList.getSelected();
                if (selected != null) {
                    String commandKey = heroItemCommandLookup.get(selected);
                    if (commandKey != null) {
                        sendCommand("wear " + commandKey, true);
                    }
                }
            }
        });
        commandActors.add(wearSelectedButton);

        TextButton dropSelectedButton = new TextButton("Drop Selected", skin);
        dropSelectedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = inventoryList.getSelected();
                if (selected != null) {
                    String commandKey = heroItemCommandLookup.get(selected);
                    if (commandKey != null) {
                        sendCommand("drop " + commandKey, true);
                    }
                }
            }
        });
        commandActors.add(dropSelectedButton);

        Table inventoryActions = new Table();
        inventoryActions.defaults().pad(3).width(130).height(40);
        inventoryActions.add(pickUpButton);
        inventoryActions.add(useSelectedButton);
        inventoryActions.add(wearSelectedButton);
        inventoryActions.add(dropSelectedButton);

        Table actionTable = new Table();
        actionTable.defaults().pad(4).width(180).height(45);
        actionTable.add(new Label("Movement", skin)).colspan(1).row();
        actionTable.add(movementTable).height(150).row();
        actionTable.add(new Label("Actions", skin)).row();
        actionTable.add(attackButton).row();
        actionTable.add(inventoryButton).row();
        actionTable.add(fightAttackButton).row();
        actionTable.add(fightPotionButton).row();
        actionTable.add(quitButton).row();

        // Command input area
        commandInput = new TextField("", skin);
        commandInput.setMessageText("Type a command (e.g. go north, attack)");
        commandInput.setTextFieldListener((textField, c) -> {
            if (c == '\n' || c == '\r') {
                sendCommand(textField.getText(), true);
                textField.setText("");
            }
        });
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendCommand(commandInput.getText(), true);
                commandInput.setText("");
            }
        });
        commandActors.add(sendButton);

        Table commandTable = new Table();
        commandTable.defaults().pad(4);
        commandTable.add(new Label("Command", skin)).left().row();
        commandTable.add(commandInput).width(400).left();
        commandTable.add(sendButton).width(120);

        // Compose layout: left column, center log, right column
        Table leftColumn = new Table();
        leftColumn.defaults().pad(6);
        leftColumn.add(heroTable).fillX().row();
        leftColumn.add(inventoryTable).fillX().row();
        leftColumn.add(inventoryActions).fillX();

        Table rightColumn = new Table();
        rightColumn.defaults().pad(6);
        rightColumn.add(roomTable).fillX().row();
        rightColumn.add(actionTable).fill();

        root.add(leftColumn).top();
        root.add(logScroll).expand().fill();
        root.add(rightColumn).top();
        root.row();
        root.add(commandTable).colspan(3).left();

        // register buttons for state tracking
        commandActors.add(northButton);
        commandActors.add(southButton);
        commandActors.add(eastButton);
        commandActors.add(westButton);
        commandActors.add(attackButton);
        commandActors.add(inventoryButton);
        commandActors.add(quitButton);
        commandActors.add(fightAttackButton);
        commandActors.add(fightPotionButton);
        commandActors.add(commandInput);
    }

    private Skin createBasicSkin() {
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        skin.add("white", texture);
        pixmap.dispose();

        skin.add("default", new Label.LabelStyle(font, Color.WHITE));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 1f));
        buttonStyle.down = skin.newDrawable("white", new Color(0.1f, 0.1f, 0.1f, 1f));
        buttonStyle.over = skin.newDrawable("white", new Color(0.25f, 0.25f, 0.25f, 1f));
        skin.add("default", buttonStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", new Color(0.26f, 0.52f, 0.96f, 0.85f));
        textFieldStyle.background = skin.newDrawable("white", new Color(0f, 0f, 0f, 0.6f));
        skin.add("default", textFieldStyle);

        List.ListStyle listStyle = new List.ListStyle();
        listStyle.font = font;
        listStyle.fontColorSelected = Color.WHITE;
        listStyle.fontColorUnselected = new Color(0.8f, 0.8f, 0.8f, 1f);
        listStyle.selection = skin.newDrawable("white", new Color(0.2f, 0.3f, 0.5f, 0.8f));
        listStyle.background = skin.newDrawable("white", new Color(0f, 0f, 0f, 0.45f));
        skin.add("default", listStyle);

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = skin.newDrawable("white", new Color(0f, 0f, 0f, 0.35f));
        skin.add("default", scrollPaneStyle);

        return skin;
    }

    private TextButton createCommandButton(String label, String command) {
        TextButton button = new TextButton(label, skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendCommand(command, true);
            }
        });
        commandActors.add(button);
        return button;
    }

    private void sendCommand(String command, boolean echo) {
        if (io == null || gameFinished) {
            return;
        }
        if (command == null || command.isBlank()) {
            return;
        }
        String trimmed = command.trim();
        if (echo) {
            appendMessage("> " + trimmed);
        }
        io.submitCommand(trimmed);
    }

    private void appendMessage(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        logArea.appendText(message + "\n");
        logArea.setCursorPosition(logArea.getText().length());
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
        java.util.List<String> display = items.stream()
                .sorted(Comparator.comparing(Item::getItemName))
                .map(item -> buildDisplayLabel(item, heroItemCommandLookup))
                .collect(Collectors.toList());
        Array<String> inventoryArray = new Array<>(String.class);
        inventoryArray.addAll(display.toArray(new String[0]));
        inventoryList.setItems(inventoryArray);
    }

    private void updateRoomDetails(Room room) {
        if (room == null) {
            roomDescriptionLabel.setText("Room: ");
            enemyLabel.setText("Enemy: None");
            roomItemsList.setItems(new Array<>(String.class));
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
        java.util.List<String> roomItems = room.getItems().stream()
                .sorted(Comparator.comparing(Item::getItemName))
                .map(item -> buildDisplayLabel(item, roomItemCommandLookup))
                .collect(Collectors.toList());
        Array<String> roomArray = new Array<>(String.class);
        roomArray.addAll(roomItems.toArray(new String[0]));
        roomItemsList.setItems(roomArray);
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
        for (Actor actor : commandActors) {
            actor.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
            if (actor instanceof TextButton button) {
                button.setDisabled(true);
            }
            if (actor instanceof TextField field) {
                field.setDisabled(true);
            }
        }
        appendMessage(heroWon ? "You cleared the dungeon!" : "Game over. The dungeon remains dangerous.");
    }

    private String nullToUnknown(String value) {
        return value == null || value.isBlank() ? "Unknown" : value;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        if (stage != null) {
            stage.dispose();
        }
        if (skin != null) {
            Texture texture = skin.get("white", Texture.class);
            if (texture != null) {
                texture.dispose();
            }
            skin.dispose();
        }
    }
}
