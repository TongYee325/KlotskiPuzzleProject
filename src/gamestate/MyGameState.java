package gamestate;

import save.GameSave;
import save.SaveManager;
import audio.AudioManager;
import frame.MenuFrame;
import frame.dialog.DefaultDialog;
import level.*;

import javax.swing.*;
import java.util.ArrayList;

public class MyGameState extends GameStateBase {
    private LogSystem myLogSystem;

    private int gameMapIndex=0;


    private int saveTime=30*1000;//自动存档时间
    //计时剩余时间
    public static final long TIME_LIMIT = 10* 60 * 1000; // 10 分钟

    private SaveManager mySaveManager;


    private String currentUserId; // 当前登录用户ID(null指游客)
    private int currentLevel = 0;
    private LevelBase level;


    //setting设置值
    private boolean autoSave = true;


    private float musicVolume = 1.0f;
    private boolean musicEnabled = true;



    private boolean timedMode = false;

    //bgm
    private AudioManager myAudioManager;
    private final String bgmPath = "./fx/menuBgm.wav";


    public MyGameState() {
        myLogSystem = new LogSystem();
        mySaveManager = new SaveManager(this);


        //turn on bgm
        myAudioManager = new AudioManager();
        playBgm(bgmPath,true);
    }

    public void playBgm(String bgmPath,boolean loop){
        if(musicEnabled){
            myAudioManager.play(bgmPath,true);
        }
    }

    public void updateBgmConfig()
    {

        if(musicEnabled&&!myAudioManager.isPlaying()) {
            myAudioManager.play(bgmPath,true);
            myAudioManager.setVolume(musicVolume);
        }else if(musicEnabled&&myAudioManager.isPlaying()) {
            myAudioManager.setVolume(musicVolume);
        } else{
            myAudioManager.stop();
            myAudioManager.setVolume(musicVolume);
        }
    }


    public void stopBgm(){
        myAudioManager.stop();
    }

    public void startLevel(int levelIndex) {
        if (levelIndex == currentLevel && levelIndex != 0) {
            //传入关卡与当前关卡一样时，不做任何处理
            return;
        }
        if (level != null) {
            level.levelDestroy();
            level = null;
        }
        currentLevel = levelIndex;
        switch (currentLevel) {
            case 0:
                level = new LoginLevel(this);
                break;
            case 1:
                level = new MenuLevel(this);
                break;
            case 2:
                level = new GameLevel(this,gameMapIndex,isTimedMode());
                break;
        }
        if (level != null) {
            level.levelInit();
            level.levelStart();
        }
    }

    public void saveGameData(){
        mySaveManager.saveGame();
    }


    public void loadGameData(MenuFrame menuFrame) {
        int[] info = new int [1];
        GameSave gamesave= mySaveManager.loadGame(info);
            switch (info[0]) {
            case 1: // 游客登录，无法加载存档
                showMessage("Please log in to load your saved game",menuFrame);
                break;

            case 2: // 存档不存在
                showMessage("Save file not found",menuFrame);
                break;

            case 3: // 存档损坏
                showMessage("The save file is corrupted. Please start a new game",menuFrame);
                break;

            default:
                if (gamesave != null) {
                    ((MenuLevel) menuFrame.getRlevel()).levelDestroy();

                    //将存档数据转化为可用数据
                    int[][] panelMap = gamesave.getPanelMap();
                    int levelIndex = gamesave.getCurrentLevelIndex();
                    ArrayList<Step> totalSteps = gamesave.getTotalSteps();
                    long elapsedTime = gamesave.getElapsedTime();
                    boolean isTimeMode = gamesave.isTimeMode();
                    long remain = gamesave.getRemainTime();


                    myLogSystem.setTotalSteps(totalSteps);
                    myLogSystem.setElapsedTime(elapsedTime);

                    if (levelIndex >= 2) {
                        if (levelIndex == currentLevel) {
                            //传入关卡与当前关卡一样时，不做任何处理
                            return;
                        }
                        currentLevel = levelIndex;
                        switch (currentLevel) {
                            case 2:
                                level = new GameLevel(this,isTimeMode);
                                ((GameLevel) level).loadGame(panelMap,remain);
                                break;
                            default:
                                break;

                        }
                    }
                }
                break;
            }
    }
    private void showMessage(String message,MenuFrame menuFrame) {
        SwingUtilities.invokeLater(() -> {
            new DefaultDialog(
                    menuFrame,
                    "Load Game Error",
                    true,
                    message,
                    " ",
                    400,200
            );
        });}


    public void setGameMapIndex(int gameMapIndex) {
        this.gameMapIndex = gameMapIndex;
    }

    public SaveManager getMySaveManager() {
        return mySaveManager;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    // 设置当前用户
    public void setCurrentUser(String userId) {
        if(userId ==null) {
            currentUserId = null;
            return;
        }
        this.currentUserId = userId;
        mySaveManager.updateSavePathAccordingToUserName(userId);
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        updateBgmConfig();
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
        updateBgmConfig();

    }


    public void setTimedMode(boolean timedMode) {
        this.timedMode = timedMode;
    }

    public boolean isTimedMode() {
        return timedMode;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public int getSaveTime() {
        return saveTime;
    }

    public LevelBase getLevel() {
        return level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public LogSystem getMyLogSystem() {
        return myLogSystem;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }
    // 在MyGameState类中确保有这些方法


}

