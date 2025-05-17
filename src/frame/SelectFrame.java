package frame;

import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectFrame extends FrameBase {
    private JPanel multipleLevelPanel;
    private final int levelNum = 9;//todo
    private MenuLevel rLevel;


    private JRadioButton timeMode;
    private JRadioButton normalMode;
    public boolean isTimedModeSelected = false; // 新增：记录模式选择
    private JPanel mainPanel;

    private final String backPath = "./img/button/back.png";
    private final String back_rollover = "./img/button/back_rollover.png";
    private final String back_pressed = "./img/button/back_pressed.png";

    public SelectFrame(LevelBase level, String title, int width, int height,String imgPath) {
        super(level, title, width, height);

        this.rLevel = (MenuLevel) level;
        initialComponents();

        super.setBackground(imgPath);
    }

    private void initialComponents() {
        this.setLayout(null);
        mainPanel = new JPanel(new BorderLayout(0,0));
        mainPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        mainPanel.setOpaque(false);
        this.add(mainPanel);

        // 主内容面板（居中布局）
        JPanel contentPanel = new JPanel(new GridLayout(2,1));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,30));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // 1. 原有关卡选择部分
        addLevelSelection(contentPanel);

        // 2. 新增模式选择部分（放置在关卡选择下方）
        addModeSelection(contentPanel);

        contentPanel.setOpaque(false);

    }

    private void addLevelSelection(JPanel container) {
        /*GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 20, 0); // 下方留空20px
        gbc.anchor = GridBagConstraints.CENTER;

        // 关卡选择标题（英文）
        JLabel levelLabel = new JLabel("Select Level");
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(levelLabel, gbc);

        // 关卡按钮面板（水平排列）
        multipleLevelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        multipleLevelPanel.setOpaque(false);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(multipleLevelPanel, gbc);*/
        JPanel levelSelectionPanel = new JPanel(null);
        levelSelectionPanel.setOpaque(false);
        JLabel levelLabel = new JLabel("Select Level");

        levelLabel.setFont(new Font("Algerian", Font.BOLD, 30));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setBounds(75,20,400,50);
        levelSelectionPanel.add(levelLabel);

        multipleLevelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        multipleLevelPanel.setBounds(0,70,550,200);
        multipleLevelPanel.setOpaque(false);
        levelSelectionPanel.add(multipleLevelPanel);
        // 创建3个关卡按钮（Level 1-3）
        for (int i = 0; i < levelNum; i++) {//todo :add more level upto 9
            JButton levelBtn = createLevelBtn(i);
            multipleLevelPanel.add(levelBtn);
        }
        container.add(levelSelectionPanel);
    }

    // 新增：模式选择逻辑
    private void addModeSelection(JPanel container) {
        /*GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 30, 0); // 上下留空
        gbc.anchor = GridBagConstraints.CENTER;

        // 模式选择标题（英文）
        JLabel modeLabel = new JLabel("Select Mode");
        modeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        container.add(modeLabel, gbc);

        // 模式单选按钮组（水平排列）
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        ButtonGroup modeGroup = new ButtonGroup();

        // 限时模式按钮（英文）
        JRadioButton timedMode = new JRadioButton("Timed Mode");
        timedMode.addActionListener(e -> isTimedModeSelected = true);
        modeGroup.add(timedMode);
        modePanel.add(timedMode);

        // 普通模式按钮（英文，默认选中）
        JRadioButton normalMode = new JRadioButton("Normal Mode", true);

        normalMode.addActionListener(e -> isTimedModeSelected = false);
        modeGroup.add(normalMode);
        modePanel.add(normalMode);




        gbc.gridy = 3;
        container.add(modePanel, gbc);

        timedMode.setOpaque(false);
        normalMode.setOpaque(false);
        modePanel.setOpaque(false);*/
        JPanel modeSelectionPanel = new JPanel(null);
        JLabel modeLabel = new JLabel("Select Mode");

        modeLabel.setFont(new Font("Algerian", Font.BOLD, 30));
        modeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        modeSelectionPanel.add(modeLabel);


        timeMode = new JRadioButton("Timed Mode");
        timeMode.setFont(new Font("Book Antiqua", Font.BOLD, 15));
        timeMode.setHorizontalAlignment(SwingConstants.CENTER);
        timeMode.addActionListener(e -> {
             setTimedModeSelected(true);
        });

        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

        normalMode = new JRadioButton("Normal Mode", true);
        normalMode.setFont(new Font("Book Antiqua", Font.BOLD, 15));
        normalMode.setHorizontalAlignment(SwingConstants.CENTER);
        normalMode.addActionListener(e -> {
            setTimedModeSelected(false);
        });

        modeSelectionPanel.add(modeLabel);
        modeSelectionPanel.add(timeMode);
        modeSelectionPanel.add(normalMode);
        modeLabel.setBounds(75,20,400,50);
        timeMode.setBounds(100,100,150,50);
        normalMode.setBounds(275,100,150,50);

        timeMode.setOpaque(false);
        normalMode.setOpaque(false);
        modeSelectionPanel.setOpaque(false);


        JButton back = new JButton("Back");
        back.addActionListener(e -> rLevel.switchToMenuFrame());
        back.setPreferredSize(new Dimension(120, 40));

        modeSelectionPanel.add(back);


        back.setBounds(200,200,130,40);
        super.setButtonBackground(back,backPath,back_rollover,back_pressed);


        container.add(modeSelectionPanel);
    }


    public void setTimedModeSelected(boolean timedModeSelected) {
        if(timedModeSelected) {
            timeMode.setSelected(true);
            normalMode.setSelected(false);
        }else {
            timeMode.setSelected(false);
            normalMode.setSelected(true);
        }
        isTimedModeSelected = timedModeSelected;
    }

    // 原有关卡按钮创建逻辑（修改点击事件，传递模式选择）
    private JButton createLevelBtn(int index) {
        String boxPath = "./img/box/box" + (index+1) + ".png";
        String boxRolloverPath = "./img/box/box" + (index+1) + "_rollover.png";
        String boxPressedPath = "./img/box/box" + (index+1) + "_pressed.png";
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(60, 60));
        btn.addActionListener(e -> {
            // 在切换关卡前，保存模式选择到游戏状态
            rLevel.getrGameState().setTimedMode(isTimedModeSelected);
            rLevel.switchToGameLevelAccordingToIndex(index);
        });
        super.setButtonBackground(btn,boxPath,boxRolloverPath,boxPressedPath);
        return btn;
    }

}
