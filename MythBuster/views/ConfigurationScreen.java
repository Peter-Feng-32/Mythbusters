package views;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import gamefiles.Difficulty;
import gamefiles.StartingWeapon;
import javafx.stage.Stage;
import sounds.BackgroundMusic;

public class ConfigurationScreen {
    private int width;
    private int height;
    private Label header;
    private Button beginButton;
    private ComboBox<StartingWeapon> startingWeaponSelector;
    private ComboBox<Difficulty> difficultySelector;
    private TextField heroNameTextField;
    private Slider volumeControl;

    private ConfigurationScreen() { }

    /**
     * Create Configuration Screen.
     * @param width width of screen
     * @param height height of screen
     */
    public ConfigurationScreen(int width, int height) {
        this.width = width;
        this.height = height;
        beginButton = new Button("Begin!");
        beginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30; -fx-background-image: url('sprites/buttonSkin.png'); -fx-font-family: Papyrus");
        heroNameTextField = new TextField();
        heroNameTextField.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        heroNameTextField.setId("HeroNameTextField");
        startingWeaponSelector = new ComboBox<StartingWeapon>();
        difficultySelector = new ComboBox<Difficulty>();
        volumeControl = new Slider(0.0, 2.0, 
                    BackgroundMusic.getVolume() / BackgroundMusic.getVolumeMarker());

        header = new Label("Configuration Screen");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-font-family: Papyrus");
    }

    /**
     * Get Configuration Screen Scene.
     * @return configuration screen scene
     */
    public Scene getScene() {
        heroNameTextField.setId("HeroNameTextField");
        heroNameTextField.setPrefWidth(350);
        heroNameTextField.setFont(new Font(30));
        heroNameTextField.setPromptText("Name your hero");

        startingWeaponSelector.setId("startingWeaponSelector");
        startingWeaponSelector.setPrefWidth(300);
        startingWeaponSelector.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        startingWeaponSelector.getItems().setAll(StartingWeapon.values());
        startingWeaponSelector.getSelectionModel().selectFirst();

        difficultySelector.setId("difficultySelector");
        difficultySelector.setPrefWidth(300);
        difficultySelector.setStyle("-fx-font-size: 30; -fx-font-family: Papyrus");
        difficultySelector.getItems().setAll(Difficulty.values());
        difficultySelector.getSelectionModel().selectFirst();

        volumeControl.setId("volumeControlId");
        volumeControl.setPrefWidth(300);
        volumeControl.setMajorTickUnit(.25);
        volumeControl.setShowTickLabels(true);
        volumeControl.showTickMarksProperty();
        HBox volumeBox = new HBox(volumeControl);
        volumeBox.setAlignment(Pos.CENTER);
        volumeBox.setLayoutY(550);
        volumeBox.setLayoutX(100);

        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(heroNameTextField, startingWeaponSelector, difficultySelector);
        hBox.setMinWidth(width);
        hBox.setLayoutY(300);

        Label heroNameDescription = new Label("Hero Name");
        heroNameDescription.layoutXProperty().bind(heroNameTextField.layoutXProperty());
        heroNameDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        heroNameDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center; -fx-font-family: Papyrus");

        heroNameDescription.setPrefWidth(350);
        heroNameDescription.setTextFill(Color.WHITE);

        Label startingWeaponDescription = new Label("Weapon");
        startingWeaponDescription.layoutXProperty()
                .bind(startingWeaponSelector.layoutXProperty());
        startingWeaponDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        startingWeaponDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center; -fx-font-family: Papyrus");
        startingWeaponDescription.setPrefWidth(300);
        startingWeaponDescription.setTextFill(Color.WHITE);

        Label difficultyDescription = new Label("Difficulty");
        difficultyDescription.layoutXProperty().bind(difficultySelector.layoutXProperty());
        difficultyDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        difficultyDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center; -fx-font-family: Papyrus");
        difficultyDescription.setPrefWidth(300);
        difficultyDescription.setTextFill(Color.WHITE);

        Label volumeDescription = new Label("Volume");
        volumeDescription.layoutXProperty().bind(volumeBox.layoutXProperty());
        volumeDescription.layoutYProperty().bind(volumeBox.layoutYProperty().subtract(50));
        volumeDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center; -fx-font-family: Papyrus");
        volumeDescription.setPrefWidth(300);
        volumeDescription.setTextFill(Color.WHITE);

        Group descriptionGroup = new Group(heroNameDescription,
                startingWeaponDescription, difficultyDescription, volumeDescription);

        VBox beginButtonVBox = new VBox(beginButton);
        beginButtonVBox.setAlignment(Pos.CENTER);
        beginButtonVBox.setMinWidth(width);
        beginButtonVBox.setLayoutY(700);

        Group board = new Group();
        board.getChildren().addAll(header, hBox, descriptionGroup, volumeBox, beginButtonVBox);

        VBox root = new VBox();
        root.getChildren().addAll(board);
        root.setStyle("-fx-background-image: url('sprites/characterSelection.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        Scene scene = new Scene(root, width, height);
        return scene;
    }

    /**
     * Get button to begin game.
     * @return button to begin game
     */
    public Button getBeginButton() {
        return beginButton;
    }

    /**
     * Get Hero Name TextField
     * @return hero name TextField
     */
    public TextField getHeroNameField() {
        return heroNameTextField;
    }

    /**
     * Get Weapon Selector Box.
     * @return weapon selector box
     */
    public ComboBox<StartingWeapon> getStartingWeaponSelector() {
        return startingWeaponSelector;
    }

    /**
     * Get Difficulty selector box.
     * @return difficulty selector
     */

    public ComboBox<Difficulty> getDifficultySelector() {
        return difficultySelector;
    }

    public Slider getVolumeControl() {
        return volumeControl;
    }

    /**
     * Set necessary bindings properties.
     * @param stage stage to bind to
     */
    public void setBinds(Stage stage) {
        header.minWidthProperty().bind(stage.widthProperty());
    }
}
