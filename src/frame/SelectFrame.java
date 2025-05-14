package frame;

import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectFrame extends FrameBase {
    private JPanel multipleLevelPanel;
    private int levelNum = 3;
    private MenuLevel rLevel;
    public boolean isTimedModeSelected = false; // 新增：记录模式选择

    public SelectFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.rLevel = (MenuLevel) level;
        this.setLayout(new BorderLayout(20, 20)); // 使用更灵活的BorderLayout

        // 主内容面板（居中布局）
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(contentPanel, BorderLayout.CENTER);

        // 1. 原有关卡选择部分
        addLevelSelection(contentPanel);

        // 2. 新增模式选择部分（放置在关卡选择下方）
        addModeSelection(contentPanel);

        // 3. 原有返回按钮（调整位置到底部）
        addBackButton();
    }

    // 原有关卡选择逻辑（调整为GridBagLayout布局）
    private void addLevelSelection(JPanel container) {
        GridBagConstraints gbc = new GridBagConstraints();
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
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(multipleLevelPanel, gbc);

        // 创建3个关卡按钮（Level 1-3）
        for (int i = 0; i < levelNum; i++) {
            JButton levelBtn = createLevelBtn(i);
            multipleLevelPanel.add(levelBtn);
        }
    }

    // 新增：模式选择逻辑
    private void addModeSelection(JPanel container) {
        GridBagConstraints gbc = new GridBagConstraints();
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
    }

    // 原有返回按钮（调整位置到底部）
    private void addBackButton() {
        JButton back = new JButton("Back");
        back.addActionListener(e -> rLevel.switchToMenuFrame());
        back.setPreferredSize(new Dimension(120, 40));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomPanel.add(back);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 原有关卡按钮创建逻辑（修改点击事件，传递模式选择）
    private JButton createLevelBtn(int index) {
        JButton btn = new JButton("Level " + (index + 1));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.addActionListener(e -> {
            // 在切换关卡前，保存模式选择到游戏状态
            rLevel.getrGameState().setTimedMode(isTimedModeSelected);
            rLevel.switchToGameLevelAccordingToIndex(index, isTimedModeSelected);
        });
        return btn;
    }
    // 在SelectFrame类中添加这个公共方法
    public boolean isTimedModeSelected() {
        return isTimedModeSelected;
    }
}
