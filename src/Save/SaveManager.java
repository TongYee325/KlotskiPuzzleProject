package Save;

import gamestate.MyGameState;
import level.GameLevel;
import level.MenuLevel;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SaveManager {
    private Path savePath;
    private String username;
    private MyGameState rGameState;


    public SaveManager(MyGameState gameState) {
        this.rGameState = gameState;
    }

    public void updateSavePathAccordingToUserName(String username) {
        this.username = username;
        this.savePath = Paths.get("user", username, "cluster");
    }

    public void saveGame() {
        GameSave gameSave;
        switch (rGameState.getCurrentLevel()) {
            case 2:
                gameSave = new GameSave(((GameLevel) rGameState.getLevel()).getGameFrame().getGamePanel().getPanelMap(),
                        rGameState.getMyLogSystem().getTotalSteps(),
                        rGameState.getCurrentLevel(),
                        ((GameLevel) rGameState.getLevel()).getGameFrame().getElapsedTime());
                saveData(gameSave);
                break;
            default:
                break;

        }
    }

    private void saveData(GameSave gameSave) {
        try {
            Files.createDirectories(savePath);
            Path dataPath = savePath.resolve("save.dat");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataPath.toFile()))) {
                oos.writeObject(gameSave);
            } catch (IOException e) {
                System.out.println("Error saving game");
            }
        } catch (IOException e) {
            System.out.println("Error saving game");
            throw new RuntimeException(e);
        }
    }

    public GameSave loadGame(int[] info) {
        if (username == null) {
            info[0] = 1;
            return null;//游客登陆不可用}
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(savePath.resolve("save.dat").toFile())))
        {
            info[0] = 0;
            return (GameSave) ois.readObject();
        } catch (FileNotFoundException e) {
            info[0] = 2;
            return null;//存档不存在
        } catch (Exception e) {
            //存档损坏
            info[0] = 3;
            return null;
        }
    }

}
