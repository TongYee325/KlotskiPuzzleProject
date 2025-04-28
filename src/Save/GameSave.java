package Save;

import level.Step;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameSave implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //存储的数据
    private int [][]panelMap;//现在的棋盘状态
    private ArrayList<Step> totalSteps;//已走数据
    private int currentLevelIndex;//现在的关卡索引

    public GameSave(int [][]panelMap, ArrayList<Step> totalSteps, int currentLevelIndex) {
        this.panelMap = panelMap;
        this.totalSteps = totalSteps;
        this.currentLevelIndex = currentLevelIndex;
    }

}
