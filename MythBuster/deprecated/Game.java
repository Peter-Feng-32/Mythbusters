/*


package deprecated;

import gamefiles.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gamefiles.StartingWeapon;
import gamefiles.weapons.WeaponDatabase;

public class Game extends Application {

    private static final double W = 1200;
    private static final double H = 800;

    private Group board;

    private String name;
    private Difficulty difficulty;


    private Text nameDisplay;
    private Text weaponDisplay;
    private Text coinDisplay;

    private Player p1 = new Player(0);


    */
/**
     * This is the main method to launch the application.
     *
     * @param args unused
     **//*

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WeaponDatabase.initialize();

        stage.setResizable(false);
    }

    */
/**
     * Set initial parameters
     *
     * @param nameEntry           the name of the hero
     * @param startingWeaponIndex the index of the starting weapon
     * @param difficultyEntry     the difficulty
     *//*

    private void initializeStats(String nameEntry,
                                 int startingWeaponIndex, Difficulty difficultyEntry) {
        name = nameEntry;
        p1.setWeapon(WeaponDatabase.getWeapon(startingWeaponIndex));
        difficulty = difficultyEntry;
        switch (difficulty) {
        case EASY:
            p1.setCoins(30);
            break;
        case MEDIUM:
            p1.setCoins(20);
            break;
        case HARD:
            p1.setCoins(10);
            break;
        default: // unnecessary because of type safety
        }
    }


    */
/**
     * This is the configuration screen.
     *
     * @param stage the stage
     *//*

    private void configurationScreen(Stage stage) {
        Label header = new Label("Configuration Screen");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
        header.minWidthProperty().bind(stage.widthProperty());

        TextField heroNameTextField = new TextField();
        heroNameTextField.setPrefWidth(350);
        heroNameTextField.setFont(new Font(30));
        heroNameTextField.setPromptText("Name your hero");

        ComboBox<StartingWeapon> startingWeaponSelector = new ComboBox<StartingWeapon>();
        startingWeaponSelector.setPrefWidth(300);
        startingWeaponSelector.setStyle("-fx-font-size: 30");
        startingWeaponSelector.getItems().setAll(StartingWeapon.values());
        startingWeaponSelector.getSelectionModel().selectFirst();

        ComboBox<Difficulty> difficultySelector = new ComboBox<Difficulty>();
        difficultySelector.setPrefWidth(300);
        difficultySelector.setStyle("-fx-font-size: 30");
        difficultySelector.getItems().setAll(Difficulty.values());
        difficultySelector.getSelectionModel().selectFirst();

        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(heroNameTextField, startingWeaponSelector, difficultySelector);
        hBox.minWidthProperty().bind(stage.widthProperty());
        hBox.setLayoutY(300);

        Label heroNameDescription = new Label("Hero Name");
        heroNameDescription.layoutXProperty().bind(heroNameTextField.layoutXProperty());
        heroNameDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        heroNameDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");

        heroNameDescription.setPrefWidth(350);

        Label startingWeaponDescription = new Label("Starting GameFiles.Weapon");
        startingWeaponDescription.layoutXProperty()
                .bind(startingWeaponSelector.layoutXProperty());
        startingWeaponDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        startingWeaponDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");
        startingWeaponDescription.setPrefWidth(300);

        Label difficultyDescription = new Label("Difficulty");
        difficultyDescription.layoutXProperty().bind(difficultySelector.layoutXProperty());
        difficultyDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        difficultyDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");
        difficultyDescription.setPrefWidth(300);

        Group descriptionGroup = new Group(heroNameDescription,
                startingWeaponDescription, difficultyDescription);

        Button beginButton = new Button("Begin");
        beginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");
        beginButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            if (heroNameTextField.getText().length() < 1
                    || heroNameTextField.getText().trim().isEmpty()) {
                showAlert("Your name cannot be empty or whitespace only!");
                return;
            }
            initializeStats(heroNameTextField.getText(),
                startingWeaponSelector.getSelectionModel().getSelectedIndex(),
                    difficultySelector.getValue());
            gameScreen(stage);
        });

        VBox beginButtonVBox = new VBox(beginButton);
        beginButtonVBox.setAlignment(Pos.CENTER);
        beginButtonVBox.minWidthProperty().bind(stage.widthProperty());
        beginButtonVBox.setLayoutY(700);

        board = new Group();
        board.getChildren().addAll(header, hBox, descriptionGroup, beginButtonVBox);
        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.show();

    }

    */
/**
     * This is the game screen.
     *
     * @param stage the stage
     *//*

    public void gameScreen(Stage stage) {
        nameDisplay = new Text(110, 10, "Name: " + name);
        nameDisplay.setStyle("-fx-font-size: 30;");
        weaponDisplay = new Text(210, 10, "GameFiles.Weapon: " + p1.getWeapon().getName());
        weaponDisplay.setStyle("-fx-font-size: 30;");
        coinDisplay = new Text(310, 10, "Coins: " + p1.getCoins());
        coinDisplay.setStyle("-fx-font-size: 30;");

        HBox displays = new HBox(50, nameDisplay, weaponDisplay, coinDisplay);
        displays.setLayoutX(10);
        displays.setLayoutY(20);

        Door exit1 = new Door(1100, 100, null);
        Door exit2 = new Door(1100, 300, null);
        Door exit3 = new Door(1100, 500, null);
        Door exit4 = new Door(1100, 700, null);

        board = new Group();
        board.getChildren().addAll(displays);
        board.getChildren().addAll(exit1.getGroup(),
                exit2.getGroup(), exit3.getGroup(), exit4.getGroup());
        Scene scene = new Scene(board, W, H, Color.PURPLE);
        stage.setScene(scene);
        stage.show();
    }

    */
/**
     * Alert if the hero name is invalid.
     * @param message Message to display in alert.
     *//*

    public void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING, message);
        alert.showAndWait();
        return;
    }
}
*/
