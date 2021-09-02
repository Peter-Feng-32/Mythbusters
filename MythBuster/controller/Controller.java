package controller;

import gamefiles.Difficulty;
import gamefiles.Door;
import gamefiles.Inventory;
import gamefiles.StartingWeapon;
import gamefiles.characters.Player;
import gamefiles.characters.Trap;
import gamefiles.items.ItemDatabase;
import gamefiles.rooms.BossRoom;
import gamefiles.rooms.ChallengeRoom;
import gamefiles.rooms.Room;
import gamefiles.rooms.RoomLayout;
import gamefiles.weapons.Bow;
import gamefiles.weapons.WeaponDatabase;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sounds.BackgroundMusic;
import views.*;

import java.util.ArrayList;


public class Controller extends Application {
    private static Stage mainWindow;
    private static final int W = 1200;
    private static final int H = 800;
    private static Player player;
    private static GameScreen gameScreen;
    private static RoomLayout roomLayout;
    private static Room currentRoom;
    private static Room prevRoom;
    private static AnimationTimer controllerLoop;

    private static int gameDifficulty;

    private static int timeElapsed;

    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setTitle("MythBusters!");
        WeaponDatabase.initialize();
        ItemDatabase.initialize();
        BackgroundMusic.initialize();
        BackgroundMusic.getBackgroundTrack().play();
        initWelcomeScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initWelcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen(W, H);
        Scene scene = welcomeScreen.getScene();
        Button startGameButton = welcomeScreen.getStartButton();

        startGameButton.setOnAction(e -> goToConfigurationScreen());
        welcomeScreen.setBinds(mainWindow);

        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public static void goToConfigurationScreen() {
        ConfigurationScreen configScreen = new ConfigurationScreen(W, H);
        Button beginButton = configScreen.getBeginButton();
        TextField heroNameField = configScreen.getHeroNameField();
        ComboBox<StartingWeapon> startingWeaponSelector = configScreen.getStartingWeaponSelector();
        ComboBox<Difficulty> difficultySelector = configScreen.getDifficultySelector();
        Slider volumeControl = configScreen.getVolumeControl();

        beginButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            if (heroNameField.getText().length() < 1
                    || heroNameField.getText().trim().isEmpty()) {
                showAlert("Your name cannot be empty or whitespace only!");
                return;
            }
            setDifficulty(difficultySelector.getValue());
            initializeStats(heroNameField.getText(),
                    startingWeaponSelector.getSelectionModel().getSelectedIndex(),
                    difficultySelector.getValue());
            goToStartingRoom();
        });

        volumeControl.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                    BackgroundMusic.setVolume(newValue.doubleValue());
            });

        Scene scene = configScreen.getScene();
        mainWindow.setScene(scene);
        configScreen.setBinds(mainWindow);

    }

    public static void goToInventory() {
        Scene scene = Inventory.getScene();
        mainWindow.setScene(scene);
    }

    public static void goToStartingRoom() {
        //Initialize starting room.
        roomLayout = new RoomLayout();
        gameScreen = new GameScreen(W, H, player, roomLayout);
        currentRoom = roomLayout.getRoom(roomLayout.getStartRoomRow(),
                roomLayout.getStartRoomColumn());
        gameScreen.updateBoard(currentRoom);
        player.moveAbsolute(W / 2, H / 2);
        Scene scene = gameScreen.getScene();
        mainWindow.setScene(scene);
        playGame();
    }

    public static void playGame() {
        //Take in inputs
        ArrayList<String> input = new ArrayList<>();

        //Keyboard shortcuts related to the overall game go here
        gameScreen.getScene().setOnKeyReleased(
            e -> {
                String code = e.getCode().toString();
                input.add(code);
            });

        BackgroundMusic.getBackgroundTrack().play();



        controllerLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                timeElapsed += 1;
                // game logic
                VBox displays = gameScreen.getDisplays();

                // if there are no monsters, unlock the doors
                if (GameLoop.getMonsters().size() == 0) {
                    currentRoom.unlockDoors();
                    if (currentRoom instanceof ChallengeRoom) {
                        ChallengeRoom c = (ChallengeRoom)(currentRoom);
                        if (c.getStatus() == 1) {
                            currentRoom.giveBonusRewards();
                            c.setStatus(2);
                        }
                    }

                }

                if(currentRoom instanceof BossRoom && !BackgroundMusic.isBossPlaying()) {
                    BackgroundMusic.getBossTrack().play();
                    BackgroundMusic.getBackgroundTrack().pause();
                    BackgroundMusic.setBossPlaying(true);
                } else if (!(currentRoom instanceof BossRoom) && BackgroundMusic.isBossPlaying()) {
                    BackgroundMusic.getBossTrack().pause();
                    BackgroundMusic.getBackgroundTrack().play();
                    BackgroundMusic.setBossPlaying(false);
                }

                //If there is a left door and we are at it.
                if (currentRoom.getLeftDoor() != null
                            && player.intersects(currentRoom.getLeftDoor())
                            && !currentRoom.getLeftDoor().isLocked()) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    prevRoom = currentRoom;
                    currentRoom =
                            roomLayout.getRoom(currentRoom.getRow(), currentRoom.getColumn() - 1);

                    for (int i = 0; i < 4; i++) {
                        Door d = currentRoom.getDoors()[i];
                        if (d != null) {
                            Room r = d.getDestination();
                            if ((r.getColumn() == prevRoom.getColumn())
                                && (r.getRow() == prevRoom.getRow())) {
                                d.unlock();
                            }
                        }
                    }
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W - 200, H / 2 - player.getHeight() / 2);
                }

                //If there is a right door and we are at it.
                if (currentRoom.getRightDoor() != null
                            && player.intersects(currentRoom.getRightDoor())
                            && !currentRoom.getRightDoor().isLocked()) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    prevRoom = currentRoom;
                    currentRoom =
                            roomLayout.getRoom(currentRoom.getRow(), currentRoom.getColumn() + 1);

                    for (int i = 0; i < 4; i++) {
                        Door d = currentRoom.getDoors()[i];
                        if (d != null) {
                            Room r = d.getDestination();
                            if ((r.getColumn() == prevRoom.getColumn())
                                && (r.getRow() == prevRoom.getRow())) {
                                d.unlock();
                            }
                        }
                    }
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(100, H / 2 - player.getHeight() / 2);
                }

                //If there is a top door and we are at it.
                if (currentRoom.getTopDoor() != null
                            && player.intersects(currentRoom.getTopDoor())
                            && !currentRoom.getTopDoor().isLocked()) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    prevRoom = currentRoom;
                    currentRoom =
                            roomLayout.getRoom(currentRoom.getRow() - 1, currentRoom.getColumn());

                    for (int i = 0; i < 4; i++) {
                        Door d = currentRoom.getDoors()[i];
                        if (d != null) {
                            Room r = d.getDestination();
                            if ((r.getColumn() == prevRoom.getColumn())
                                && (r.getRow() == prevRoom.getRow())) {
                                d.unlock();
                            }
                        }
                    }
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2 - player.getWidth() / 2, H - 200);
                }

                //If there is a bottom door and we are at it.
                if (currentRoom.getBottomDoor() != null
                            && player.intersects(currentRoom.getBottomDoor())
                            && !currentRoom.getBottomDoor().isLocked()) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    prevRoom = currentRoom;
                    currentRoom =
                            roomLayout.getRoom(currentRoom.getRow() + 1, currentRoom.getColumn());

                    for (int i = 0; i < 4; i++) {
                        Door d = currentRoom.getDoors()[i];
                        if (d != null) {
                            Room r = d.getDestination();
                            if ((r.getColumn() == prevRoom.getColumn())
                                && (r.getRow() == prevRoom.getRow())) {
                                d.unlock();
                            }
                        }
                    }
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2 - player.getWidth()/ 2, 100);
                }

            }
        };

        GameLoop.initializeAllAnimationTimers(player, gameScreen);
        if (player.getWeapon() instanceof Bow) {
            GameLoop.startAllAnimationTimers(player.getPlayerLogicTimer(),
                ((Bow) player.getWeapon()).getArrowTimer(),
                    player.getPlayerHpUpdateTimer(),
                    GameLoop.getMonsterLoop(), controllerLoop, player.getItemLoop(),
                    GameLoop.getDroppedLoop());
        } else {
            GameLoop.startAllAnimationTimers(player.getPlayerLogicTimer(),
                    player.getPlayerHpUpdateTimer(),
                    GameLoop.getMonsterLoop(), controllerLoop, player.getItemLoop(),
                    GameLoop.getDroppedLoop());
        }
    }

    public static void goToGameScreen() {
        Scene scene = gameScreen.getScene();
        mainWindow.setScene(scene);
    }

    public static void goToWinScreen() {
        if (controllerLoop != null && player != null) {
            GameLoop.stopAllAnimationTimers(player.getPlayerLogicTimer(),
            player.getPlayerHpUpdateTimer(), GameLoop.getMonsterLoop(),
            controllerLoop, player.getItemLoop(), GameLoop.getDroppedLoop());
        }
        BackgroundMusic.getBossTrack().stop();
        BackgroundMusic.setBossPlaying(false);
        BackgroundMusic.getBackgroundTrack().stop();
        Trap.setTrapCount(0);

        for (int i = 0; i < 5; i++) {
            Inventory.removeFromHotbar(i);
        }
        Inventory.setHotbarSize(0);
        Inventory.clearInventory();

        WinScreen winScreen = new WinScreen(W, H);
        player = new Player(0, null);


        Scene scene = winScreen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public static void goToBossRoom() {
        //Go to boss room(used for testing).
        currentRoom = roomLayout.getRoom(roomLayout.getBossRoomRow(),
                                        roomLayout.getBossRoomColumn());
        gameScreen.updateBoard(currentRoom);
        player.moveAbsolute(W / 2, H / 2);
    }

    public static void goToDeathScreen() {
        GameLoop.stopAllAnimationTimers(player.getPlayerLogicTimer(),
                player.getPlayerHpUpdateTimer(), GameLoop.getMonsterLoop(),
                controllerLoop, player.getItemLoop(),
                GameLoop.getDroppedLoop());
        Trap.setTrapCount(0);

        for (int i = 0; i < 5; i++) {
            Inventory.removeFromHotbar(i);
        }
        Inventory.setHotbarSize(0);
        Inventory.clearInventory();

        DeathScreen deathScreen = new DeathScreen(W, H);
        player = new Player(0, null);

        Scene scene = deathScreen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private static void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
        case EASY:
            gameDifficulty = 0;
            break;
        case MEDIUM:
            gameDifficulty = 1;
            break;
        case HARD:
            gameDifficulty = 2;
            break;
        default:
            break;
        }
    }

    public static int getDifficulty() {
        return gameDifficulty;
    }

    /**
     * Set initial parameters
     *
     * @param nameEntry           the name of the hero
     * @param startingWeaponIndex the index of the starting weapon
     * @param difficultyEntry     the difficulty
     */
    private static void initializeStats(String nameEntry,
                                 int startingWeaponIndex, Difficulty difficultyEntry) {
        player = new Player(0, WeaponDatabase.getWeapon(startingWeaponIndex));
        player.setName(nameEntry);
        switch (difficultyEntry) {
        case EASY:
            player.setCoins(30);
            break;
        case MEDIUM:
            player.setCoins(20);
            break;
        case HARD:
            player.setCoins(10);
            break;
        default: // unnecessary because of type safety
        }
    }

    /**
     * Alert Method
     * @param message Message to display in alert.
     */
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
        return;
    }

    /**
     * @return the player object
     */
    public static Player getPlayer() {
        return player;
    }

    public static views.GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Returns the current room
     * @return the current room
     */
    public static Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * @return the first room
     */
    public GameScreen getRoomOne() {
        return gameScreen;
    }

    public RoomLayout getRoomLayout() {
        return roomLayout;
    }

    public static AnimationTimer getControllerLoop() {
        return controllerLoop;
    }

    public static int getTimeElapsed() {
        return timeElapsed;
    }
    public static void setTimeElapsed(int amount) {
        timeElapsed = amount;
    }

    /**
     * Private testing method to return a String representation of the Label of the window.
     * @return the string representing the label of the window.
     */
    public static String getWindowTitle() {
        return mainWindow.getTitle();
    }
    public static Stage getMainWindow() {
        return mainWindow;
    }


    /**
     * Getter for the width of the map.
     * @return an int representing the width of the map
     */
    public static int getW() {
        return W;
    }

    /**
     * Getter for the height of the map.
     * @return an int represneting the height of the map.
     */
    public static int getH() {
        return H;
    }




}
