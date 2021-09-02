package gamefiles.rooms;

import java.util.ArrayList;
import java.util.Optional;

import controller.Controller;
import controller.GameLoop;
import gamefiles.characters.Mage;
import gamefiles.characters.Monster;
import gamefiles.characters.Soldier;
import gamefiles.characters.TrapMonster;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChallengeRoom extends Room {

    private ArrayList<Monster> monsters = new ArrayList<>();
    Group roomGroup;
    int challengeComplete;

    private static int challengesAttempted;

    public ChallengeRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        challengeComplete = 0;

    }

    @Override
    public Group getRoomGroup() {
        roomGroup = super.getRoomGroup();
        for (Monster monster : monsters) {
            roomGroup.getChildren().add(monster.getGroup());
        }
        Controller.getGameScreen().changeBackground("-fx-background-image: url('sprites/challengeBG.png'); -fx-background-repeat: stretch; -fx-background-size: 1200 800");
        return roomGroup;
    }

    public String toString() {
        return "Challenge Room";
    }

    public void startChallenge() {
        lockDoors();
        for (int i = 0; i < 1 + Controller.getDifficulty(); i++) {
            spawnSoldier();
        }
        for (int i = 0; i < 1 + (int)(2 * Math.random() * (Controller.getDifficulty() + 1)); i++) {
            spawnMage();
        }
        for (int i = 0; i < 1 + (int)(2 * Math.random() * (Controller.getDifficulty() + 1)); i++) {
            spawnTrapMonster();
        }
        for (Monster monster : monsters) {
            roomGroup.getChildren().add(monster.getGroup());
        }
        challengeComplete = 1;
        challengesAttempted++;
    }

    public Soldier spawnSoldier() {
        Soldier soldier = new Soldier();
        soldier.moveAbsolute(Math.random() * this.getWidth(), Math.random() * this.getHeight());
        monsters.add(soldier);
        GameLoop.getMonsters().add(soldier);
        return soldier;
    }
    public Mage spawnMage() {
        Mage mage = new Mage();
        mage.moveAbsolute(Math.random() * this.getWidth(), Math.random() * this.getHeight());
        monsters.add(mage);
        GameLoop.getMonsters().add(mage);
        return mage;
    }
    public TrapMonster spawnTrapMonster() {
        TrapMonster trapMonster = new TrapMonster();
        trapMonster.moveAbsolute(Math.random() * this.getWidth(), Math.random() * this.getHeight());
        monsters.add(trapMonster);
        GameLoop.getMonsters().add(trapMonster);
        return trapMonster;
    }

    public int getStatus() {
        return challengeComplete;
    }
    
    public void setStatus(int status) {
        challengeComplete = status;
    }


    public static int getChallengesAttempted() {
        return challengesAttempted;
    }
    public static void setChallengesAttempted(int amount) {
        challengesAttempted = amount;
    }

}
