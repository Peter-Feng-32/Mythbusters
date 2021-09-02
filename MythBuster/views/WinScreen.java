package views;

import controller.Controller;
import gamefiles.TreasureChest;
import gamefiles.characters.Monster;
import gamefiles.characters.Player;
import gamefiles.rooms.ChallengeRoom;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sounds.BackgroundMusic;

import java.io.File;

public class WinScreen {
    private int width;
    private int height;
    private Label header;
    private Button restartButton;
    private MediaPlayer soundEffect;

    public WinScreen(int width, int height) {
        this.width = width;
        this.height = height;

        header = new Label("Congratulations!");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-font-family: Papyrus");
        header.setPrefWidth(width);
        restartButton = new Button("Restart?");

        setSoundEffect("MythBuster/sounds/VictorySound.wav");
    }

    public Scene getScene() {
        restartButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30; -fx-background-image: url('sprites/buttonSkin.png'); -fx-font-family: Papyrus");

        VBox restartButtonVBox = new VBox(restartButton);
        restartButtonVBox.setAlignment(Pos.CENTER);
        Insets buttonInset = new Insets(600, 150, 550, 550);
        restartButtonVBox.setPadding(buttonInset);
        restartButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            Controller.goToConfigurationScreen();
        });
        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(width - 150);
        exitButton.setLayoutY(40);
        exitButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30; -fx-background-image: url('sprites/buttonSkin.png'); -fx-font-family: Papyrus");
        exitButton.setOnAction(e -> {
            Controller.getMainWindow().close();
        });

        int minutes = Controller.getTimeElapsed() / 3600;
        int seconds = Controller.getTimeElapsed() / 60 % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        Text timer = new Text(50, 250, "Time Taken: " + timeString);
        timer.setFill(Color.WHITE);
        timer.setStyle("-fx-font-family: Papyrus");
        Text monstersKilled = new Text(width / 2 + 100, 250, "Monsters Killed: " 
            + Monster.getMonstersKilled());
        monstersKilled.setStyle("-fx-font-family: Papyrus");
        monstersKilled.setFill(Color.WHITE);
        Text greedyIndex = new Text(50, 350, "Max Concurrent Coins: " + Player.getGreedyIndex());
        greedyIndex.setStyle("-fx-font-family: Papyrus");
        greedyIndex.setFill(Color.WHITE);
        Text potionsUsed = new Text(width / 2 + 100, 350, "Potions Used: " 
            + Player.getPotionsUsed());
        potionsUsed.setStyle("-fx-font-family: Papyrus");
        potionsUsed.setFill(Color.WHITE);
        Text challengesAttempted = new Text(50, 450, "Challenges Attempted: " 
            + ChallengeRoom.getChallengesAttempted());
        challengesAttempted.setStyle("-fx-font-family: Papyrus");
        challengesAttempted.setFill(Color.WHITE);
        Text chestsOpened = new Text(width / 2 + 100, 450, "Chests Opened: " 
            + TreasureChest.getChestsOpened());
        chestsOpened.setFill(Color.WHITE);
        chestsOpened.setStyle("-fx-font-family: Papyrus");
        Controller.setTimeElapsed(0);
        Monster.setMonstersKilled(0);
        Player.setGreedyIndex(0);
        Player.setPotionsUsed(0);
        ChallengeRoom.setChallengesAttempted(0);
        TreasureChest.setChestsOpened(0);
        timer.setFont(new Font(50));
        monstersKilled.setFont(new Font(50));
        greedyIndex.setFont(new Font(50));
        potionsUsed.setFont(new Font(50));
        challengesAttempted.setFont(new Font(50));
        chestsOpened.setFont(new Font(50));

        Text congratulateMessage = new Text(50, height - 80, "");
        congratulateMessage.setWrappingWidth(width - 100);
        congratulateMessage.setTextAlignment(TextAlignment.CENTER);
        congratulateMessage.prefHeight(80);
        congratulateMessage.setStyle("-fx-font-size: 35; -fx-font-style: italic; -fx-alignment:center; -fx-font-family: Papyrus");
        congratulateMessage.setFill(Color.WHITE);
        switch (Controller.getDifficulty()) {
        case 0:
            congratulateMessage.setText("Oh. You won on EASY mode!");
            break;
        case 1:
            congratulateMessage.setText("Congratulations. You beat the game on" 
                + "normal. Just like most people.");
            break;
        case 2:
            congratulateMessage.setText("Let me guess. You probably stacked" 
                + "rage potions. Really unique.");
            break;
        default:
        }
        soundEffect.seek(Duration.ZERO);
        soundEffect.play();
        Group board = new Group();
        board.getChildren().addAll(header, timer, chestsOpened, greedyIndex, monstersKilled,
            challengesAttempted, potionsUsed, restartButtonVBox, exitButton, congratulateMessage);
        
        VBox root = new VBox();
        root.getChildren().addAll(board);
        root.setStyle("-fx-background-image: url('sprites/winBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        Scene scene = new Scene(root, width, height);
    
        return scene;
    }

    public void setSoundEffect(String soundPath) {
        //Instantiating Media class
        Media media = new Media(new File(soundPath).toURI().toString());
        //Instantiating MediaPlayer class
        soundEffect = new MediaPlayer(media);
        soundEffect.setStopTime(Duration.seconds(10));
        soundEffect.setVolume(BackgroundMusic.getVolume() * 0.9);
    }
}
