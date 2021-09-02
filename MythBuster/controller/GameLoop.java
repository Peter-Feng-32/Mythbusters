package controller;

import gamefiles.Droppable;
import gamefiles.characters.Player;
import gamefiles.characters.Monster;
import gamefiles.weapons.Bow;
import gamefiles.weapons.Weapon;
import gamefiles.weapons.WeaponDatabase;
import javafx.animation.AnimationTimer;
import views.GameScreen;

import java.util.ArrayList;

public class GameLoop {

    private static ArrayList<Monster> monsters = new ArrayList<>();
    private static AnimationTimer monsterLoop;
    private static Weapon bow = WeaponDatabase.getWeapon(2);
    private static ArrayList<Droppable> drops = new ArrayList<Droppable>();
    private static AnimationTimer droppedLoop;


    public static void gameLoop() {

        monsterLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for (int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                }

            }
        };

        droppedLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for (Droppable drop : drops) {
                    drop.update();
                }
            }
        };
    }

    public static void initializeAllAnimationTimers(Player player, GameScreen gameScreen) {
        GameLoop.gameLoop();
        player.updatePlayerHp();
        ((Bow) bow).arrowTimer();
        player.play(gameScreen.getScene());
    }

    public static void startAllAnimationTimers(AnimationTimer... timers) {
        for (AnimationTimer timer : timers) {
            timer.start();
        }
    }

    public static void stopAllAnimationTimers(AnimationTimer... timers) {
        for (AnimationTimer timer : timers) {
            timer.stop();
        }
    }

    public static ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public static void setMonsters(ArrayList<Monster> newMonsters) {
        monsters = newMonsters;
    }

    public static ArrayList<Droppable> getDrops() {
        return drops;
    }

    public static void setDrops(ArrayList<Droppable> newDrops) {
        drops = newDrops;
    }

    public static AnimationTimer getMonsterLoop() {
        return monsterLoop;
    }

    public static AnimationTimer getDroppedLoop() {
        return droppedLoop;
    }
}
