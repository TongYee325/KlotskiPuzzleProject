package frame.dialog;

import javax.swing.*;
import java.awt.*;

public class DefeatDialog extends JDialog {
    public DefeatDialog(Frame owner) {
        super(owner, "游戏结束", true);
        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // 图标面板
        JPanel iconPanel = new JPanel();
        JLabel iconLabel = new JLabel(new ImageIcon("resources/defeat_icon.png")); // 可替换为实际图标
        iconPanel.add(iconLabel);
        add(iconPanel, BorderLayout.NORTH);

        // 消息面板
        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("时间已耗尽！", SwingConstants.CENTER);
        messageLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> dispose());
        okButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        okButton.setPreferredSize(new Dimension(100, 35));
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
