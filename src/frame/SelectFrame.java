package frame;

import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;

public class SelectFrame extends FrameBase {
    private JPanel multipleLevelPanel;
    private int levelNum=3;
    private MenuLevel rLevel;
    public SelectFrame(LevelBase level, String title, int width, int height) {
        super(level,title,width,height);
        this.rLevel= (MenuLevel) level;
        this.setLayout(null);
        multipleLevelPanel = new JPanel();
        multipleLevelPanel.setLayout(new GridLayout(1,3,10,10));
        this.add(multipleLevelPanel);
        int panelWidth =300;
        int panelHeight = 100;
        multipleLevelPanel.setBounds(width/2-panelWidth/2,height/3-panelHeight/2,panelWidth,panelHeight);
        JButton level1 = createLevelBtn(0);
        JButton level2 = createLevelBtn(1);
        JButton level3 = createLevelBtn(2);
        multipleLevelPanel.add(level1);
        multipleLevelPanel.add(level2);
        multipleLevelPanel.add(level3);

        JButton back = new JButton("Back");
        back.setVisible(true);
        back.addActionListener(e -> {
            rLevel.switchToMenuFrame();
        });
        this.add(back);
        back.setBounds(400,400,100,60);

    }


    private JButton createLevelBtn(int index) {
        JButton btn = new JButton("Level " + (index+1));
        btn.setVisible(true);
        btn.setText("Level " + (index+1));
        btn.addActionListener(e -> {
            rLevel.getrGameState().setGameMapIndex(index);
            rLevel.getrGameState().startLevel(2);
        });
        return btn;
    }

}
