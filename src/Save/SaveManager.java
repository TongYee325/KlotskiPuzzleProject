package Save;

import gamestate.MyGameState;
import level.GameLevel;

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
                        rGameState.getCurrentLevel());
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
            throw new RuntimeException(e);
        }
    }

    public GameSave loadGame() {
        if (username == null) return null;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(savePath.resolve("save.dat").toFile()))) {
            return (GameSave) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("未找到存档文件");
        } catch (Exception e) {
            System.out.println("存档损坏: " + e.getMessage());
        }
        return null;
    }

}
