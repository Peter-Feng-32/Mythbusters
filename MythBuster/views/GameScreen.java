package views;

import java.util.Optional;

import controller.GameLoop;
import gamefiles.Droppable;
import gamefiles.characters.Player;
import gamefiles.rooms.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;

import javafx.stage.Stage;

public class GameScreen {
    private int width;
    private int height;
    private Player player;

    private Text nameDisplay;
    private Text weaponDisplay;
    private Text coinDisplay;
    private Text roomInfo;

    private RoomLayout roomLayout;
    private Group roomGroup;
    private Group board;
    private VBox displays;
    private HBox effectDisplays;
    private HBox alerts;

    private Scene scene;

    private VBox root;


    private GameScreen() { }

    /**
     * Create Room One.
     * @param width width of room screen
     * @param height height of room screen
     * @param player player in room
     * @param roomLayout layout of rooms
     */
    public GameScreen(int width, int height, Player player, RoomLayout roomLayout) {
        this.player = player;
        this.width = width;
        this.height = height;
        this.board = new Group();
        this.roomLayout = roomLayout;
        initScene();


    }

    private void initScene() {
        nameDisplay = new Text(110, 10, "Name: " + player.getName());
        nameDisplay.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        nameDisplay.setFill(Color.WHITE);
        weaponDisplay = new Text(210, 10, "Weapon: " + player.getWeapon().getName());
        weaponDisplay.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        weaponDisplay.setFill(Color.WHITE);
        weaponDisplay.setId("weaponDisplay");
        coinDisplay = new Text(310, 10, "Coins: " + player.getCoins());
        coinDisplay.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        coinDisplay.setFill(Color.WHITE);
        coinDisplay.setId("coinDisplay");


        alerts = new HBox();

        //For text displays
        displays = new VBox(15, nameDisplay, weaponDisplay, coinDisplay);
        displays.setLayoutX(10);
        displays.setLayoutY(10);

        //For consumable displays.
        effectDisplays = new HBox(20);
        effectDisplays.setLayoutX(10);
        effectDisplays.setLayoutY(170);


        player.moveAbsolute(100, 100);

        board.getChildren().addAll(displays, alerts, effectDisplays);

        root = new VBox();
        root.getChildren().addAll(board);
        root.setStyle("-fx-background-image: url('sprites/gameplayBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");

        scene = new Scene(root, width, height);
    }


    /**
     * Get scene for Room One Screen.
     * @return room one scene
     */
    public Scene getScene() {
        return scene;
    }

    public Group getBoard() {
        return board;
    }

    public void updateBoard(Room currentRoom) {
        if (TreasureRoom.getAnimationTimer() != null) {
            TreasureRoom.getAnimationTimer().stop();
        }


        board.getChildren().clear();
        board.getChildren().addAll(currentRoom.getRoomGroup(),
                player.getGroup(), player.getHeartsBox(), player.gethotbarBox());

        alerts = new HBox();

        if (currentRoom instanceof ChallengeRoom) {
            ChallengeRoom c = (ChallengeRoom) (currentRoom);
            if (c.getStatus() == 0) {
                Button startChallenge = new Button("Start Challenge");
                startChallenge.setOnAction((ActionEvent event) -> {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Challenge Issued!");
                    a.setHeaderText("You have been issued a challenge.");
                    a.setContentText(" Would you like to accept?");
                    Optional<ButtonType> response = a.showAndWait();
                    if (response.isPresent() && response.get() == ButtonType.OK) {
                        alerts.getChildren().remove(startChallenge);
                        c.startChallenge();
                    }
                });
                
                alerts.getChildren().addAll(startChallenge);
                startChallenge.setAlignment(Pos.CENTER);
                startChallenge.setStyle("-fx-font-weight: bold; -fx-font-size: 36; -fx-background-image: url('sprites/buttonSkin.png'); -fx-font-family: Papyrus");
                alerts.setAlignment(Pos.CENTER);

                Insets buttonInset = new Insets(360, 240, 480, 480);

                alerts.setPadding(buttonInset);
            }
            
        }
        if (currentRoom instanceof BossRoom) {
            System.out.println("this is a boss room");
            currentRoom.lockDoors();
        }
        board.getChildren().addAll(displays, alerts, effectDisplays);

        GameLoop.getMonsters().clear();
        GameLoop.getMonsters().addAll(currentRoom.getMonsters());

        GameLoop.getDrops().clear();
        GameLoop.getDrops().addAll(currentRoom.getDrops());
    }

    public void updateBoardDrop(Droppable drop) {
        Platform.runLater(() -> {
            board.getChildren().add(drop.getGroup());
        });
    }

    /**
     * Set necessary binding properties.  Currently placeholder.
     * @param stage stage to bind to
     */
    public void setBinds(Stage stage) {
        //PlaceHolder for binding widths.
    }

    public VBox getDisplays() {
        return displays;
    }

    public HBox getEffectDisplays() {
        return effectDisplays;
    }

    public void changeBackground(String s) {
        root.setStyle(s);
    }

    /**
     * @return the weapon display
     */
    public Text getWeaponDisplay() {
        return weaponDisplay;
    }

    /**
     * @return the coin display
     */
    public Text getCoinDisplay() {
        return coinDisplay;
    }

    /**
     * @return the name display
     */
    public Text getNameDisplay() {
        return nameDisplay;
    }
}
