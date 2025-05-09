package frame;

import frame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectDialog extends JDialog {
    private boolean isTimedModeSelected = false;

    public ModeSelectDialog(GameFrame parentFrame) {
        super(parentFrame, "选择游戏模式", true);
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JLabel modeLabel = new JLabel("请选择游戏模式：", SwingConstants.CENTER);
        modeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));

        JButton timedModeBtn = new JButton("限时模式");
        timedModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTimedModeSelected = true;
                dispose(); // 关闭对话框
            }
        });

        JButton normalModeBtn = new JButton("普通模式");
        normalModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTimedModeSelected = false;
                dispose(); // 关闭对话框
            }
        });

        add(modeLabel);
        add(timedModeBtn);
        add(normalModeBtn);
    }

    public boolean isTimedModeSelected() {
        return isTimedModeSelected;
    }
}