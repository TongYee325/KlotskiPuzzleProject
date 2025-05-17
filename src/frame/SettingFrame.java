package frame;

import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SettingFrame extends FrameBase{
    private JPanel upPanel;
    private JPanel mainPanel;
    private JButton backButton;
    private JLabel needAutoSaveLabel;
    private JCheckBox autoSaveCheckBox;
    private JLabel needTurnOnMusicLabel;
    private JCheckBox musicOnCheckBox;
    private JLabel volumeLabel;
    private JSlider volumeSlider;

    private MenuLevel rLevel;

    private final String backPath = "./img/button/back.png";
    private final String backRolloverPath = "./img/button/back_Rollover.png";
    private final String backPressedPath = "./img/button/back_Pressed.png";

    private final String boxPath = "./img/checkbox/checkbox.png";
    private final String boxRolloverPath = "./img/checkbox/checkbox_rollover.png";
    private final String boxPressedPath = "./img/checkbox/checkbox_pressed.png";
    private final String sBoxPath = "./img/checkbox/checkbox_selected.png";
    private final String sBoxRolloverPath = "./img/checkbox/checkbox_selected_rollover.png";
    private final String sBoxPressedPath = "./img/checkbox/checkbox_selected_pressed.png";


    public SettingFrame(LevelBase level, String title, int width, int height,String imgPath) {
        super(level, title, width, height);
        this.rLevel=((MenuLevel) level);

        initialComponents();
        super.setBackground(imgPath);
    }

    private void initialComponents(){
        this.setLayout(null);
        mainPanel = new JPanel(new GridLayout(3,2,20,50));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            rLevel.switchToMenuFrame();
        });


        volumeLabel = new JLabel("Volume Value");
        volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumeLabel.setFont(new Font("ArtifaktElement-Medium", Font.BOLD, 20));
        volumeLabel.setForeground(new Color(0,150,255,250));
        volumeSlider = new JSlider();
        volumeSlider.setValue((int) (rLevel.getrGameState().getMusicVolume()*100));
        volumeSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

        volumeSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(rLevel!=null){
                    rLevel.getrGameState().setMusicVolume(Math.clamp(volumeSlider.getValue(),0,100)/100.0f);
                }
            }
        });
        volumeSlider.setOpaque(false);
        needTurnOnMusicLabel = new JLabel("Music");
        needTurnOnMusicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

        needTurnOnMusicLabel.setFont(new Font("ArtifaktElement-Medium", Font.BOLD, 20));
        needTurnOnMusicLabel.setForeground(new Color(0,150,255,250));
        musicOnCheckBox = new JCheckBox();
        musicOnCheckBox.setSelected(rLevel.getrGameState().isMusicEnabled());
        musicOnCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        musicOnCheckBox.addActionListener(e -> {
           if(rLevel!=null){
               rLevel.getrGameState().setMusicEnabled(musicOnCheckBox.isSelected());
           }
        });
        super.setCheckBoxBackground(musicOnCheckBox,boxPath,boxRolloverPath,boxPressedPath,sBoxPath,sBoxRolloverPath,sBoxPressedPath);
        autoSaveCheckBox = new JCheckBox();
        autoSaveCheckBox.setSelected(rLevel.getrGameState().isAutoSave());
        autoSaveCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        autoSaveCheckBox.addActionListener(e -> {
            if(rLevel!=null){
                rLevel.getrGameState().setAutoSave(autoSaveCheckBox.isSelected());
            }
        });
        super.setCheckBoxBackground(autoSaveCheckBox,boxPath,boxRolloverPath,boxPressedPath,sBoxPath,sBoxRolloverPath,sBoxPressedPath);
        needAutoSaveLabel = new JLabel("Auto Save");
        needAutoSaveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        needAutoSaveLabel.setFont(new Font("ArtifaktElement-Medium", Font.BOLD, 20));
        needAutoSaveLabel.setForeground(new Color(0,150,255,250));
        //透明度设置


        this.add(mainPanel);
        mainPanel.setBounds(100,100,400,300);
        this.add(backButton);
        backButton.setBounds(250,450,100,50);

        mainPanel.add(needAutoSaveLabel);
        mainPanel.add(autoSaveCheckBox);

        mainPanel.add(needTurnOnMusicLabel);
        mainPanel.add(musicOnCheckBox);

        mainPanel.add(volumeLabel);
        mainPanel.add(volumeSlider);

    }
}