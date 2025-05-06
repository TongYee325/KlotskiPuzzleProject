package frame.dialog;

import gamestate.MyGameState;
import frame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VictoryDialog extends JDialog {
    public VictoryDialog(GameFrame parentFrame) {
        super(parentFrame, "胜利！", true);
        setSize(450, 250); // 增大对话框尺寸
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(15, 15)); // 调整整体间距
        getContentPane().setBackground(new Color(245, 245, 245));

        MyGameState gameState = parentFrame.getRlevel().getrGameState();
        int totalSteps = gameState.getMyLogSystem().getTotalSteps().size();
        long totalTime = parentFrame.getElapsedTime();

        // 胜利提示（顶部）
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("你赢了！", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28)); // 增大标题字号
        titleLabel.setForeground(new Color(30, 30, 30));
        titlePanel.add(titleLabel);

        // 统计信息（中间）
        JPanel infoPanel = new JPanel(new BorderLayout(10, 10)); // 改用BorderLayout
        infoPanel.setBackground(new Color(245, 245, 245));

        // 总步数面板（居中）
        JPanel stepsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 居中布局
        JLabel stepsLabel = new JLabel("总步数：");
        stepsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField stepsField = new JTextField(String.valueOf(totalSteps), 15); // 增加文本框宽度
        stepsField.setEditable(false);
        stepsField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        stepsField.setBackground(Color.WHITE);
        stepsField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        stepsPanel.add(stepsLabel);
        stepsPanel.add(stepsField);
        infoPanel.add(stepsPanel, BorderLayout.NORTH); // 放置在上方

        // 总时间面板（居中）
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel timeLabel = new JLabel("总时间：");
        timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField timeField = new JTextField(formatTime(totalTime), 15);
        timeField.setEditable(false);
        timeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        timeField.setBackground(Color.WHITE);
        timeField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        timePanel.add(timeLabel);
        timePanel.add(timeField);
        infoPanel.add(timePanel, BorderLayout.CENTER); // 放置在中间

        // 确定按钮（底部）
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> dispose()); // 点击按钮时调用 dispose() 关闭对话框
        buttonPanel.add(confirmBtn);
        confirmBtn.setBackground(new Color(0, 120, 215));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 增大按钮字体
        confirmBtn.setPreferredSize(new Dimension(80, 35)); // 调整按钮大小
        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                confirmBtn.setBackground(new Color(0, 150, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                confirmBtn.setBackground(new Color(0, 120, 215));
            }
        });
        buttonPanel.add(confirmBtn);

        add(titlePanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String formatTime(long elapsedTime) {
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = elapsedTime / (1000 * 60 * 60);

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d秒", seconds);
        }
    }
}