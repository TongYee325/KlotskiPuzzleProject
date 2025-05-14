package frame;

import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;


public class SettingFrame extends FrameBase{
    private JPanel upPanel;
    private JPanel mainPanel;
    private JButton backButton;
    private JPanel firstPanel;
    private JLabel needAutoSaveLabel;
    private JCheckBox autoSaveCheckBox;
    private JPanel secondPanel;
    private JLabel needTurnOnMusicLabel;
    private JCheckBox musicOnCheckBox;
    private JPanel thirdPanel;
    private JLabel volumeLabel;
    private JSlider volumeSlider;

    private LevelBase rLevel;

    private final String backPath = "./img/button/back.png";
    private final String backRolloverPath = "./img/button/back_Rollover.png";
    private final String backPressedPath = "./img/button/back_Pressed.png";


    public SettingFrame(LevelBase level, String title, int width, int height,String imgPath) {
        super(level, title, width, height);
        this.rLevel=level;

        initialComponents();
        super.setBackground(imgPath);
    }

    private void initialComponents(){
        this.setLayout(null);
        mainPanel = new JPanel(new GridLayout(3,1,50,50));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
        });
        firstPanel = new JPanel(new GridLayout(1,2,20,0));
        secondPanel = new JPanel(new GridLayout(1,2,20,0));
        thirdPanel = new JPanel(new GridLayout(1,2,20,0));
        volumeLabel = new JLabel("Volume Value");
        volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumeSlider = new JSlider();
        volumeSlider.setValue((int) (rLevel.getrGameState().getMusicVolume()*100));
        volumeSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeSlider.addChangeListener(e -> {
            if(rLevel!=null){
                rLevel.getrGameState().setMusicVolume(Math.clamp(volumeSlider.getValue(),0,100)/100.0f);
            }
        });
        needTurnOnMusicLabel = new JLabel("Music");
        needTurnOnMusicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        musicOnCheckBox = new JCheckBox();
        musicOnCheckBox.setSelected(rLevel.getrGameState().isMusicEnabled());
        musicOnCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        musicOnCheckBox.addActionListener(e -> {
           if(rLevel!=null){
               rLevel.getrGameState().setMusicEnabled(musicOnCheckBox.isSelected());
           }
        });
        autoSaveCheckBox = new JCheckBox();
        autoSaveCheckBox.setSelected(rLevel.getrGameState().isAutoSave());
        autoSaveCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        autoSaveCheckBox.addActionListener(e -> {
            if(rLevel!=null){
                rLevel.getrGameState().setAutoSave(autoSaveCheckBox.isSelected());
            }
        });
        needAutoSaveLabel = new JLabel("Auto Save");
        needAutoSaveLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(mainPanel);
        mainPanel.setBounds(100,100,400,300);
        this.add(backButton);
        backButton.setBounds(250,450,100,40);
        mainPanel.add(firstPanel);
        mainPanel.add(secondPanel);
        mainPanel.add(thirdPanel);

        firstPanel.add(needAutoSaveLabel);
        firstPanel.add(autoSaveCheckBox);

        secondPanel.add(needTurnOnMusicLabel);
        secondPanel.add(musicOnCheckBox);

        thirdPanel.add(volumeLabel);
        thirdPanel.add(volumeSlider);

    }
}