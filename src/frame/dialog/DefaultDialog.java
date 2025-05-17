package frame.dialog;

import javax.swing.*;
import java.awt.*;

public class DefaultDialog extends JDialog {
    public DefaultDialog(Frame owner, String title, boolean modal, String info1, String info2) {
        super(owner, title,modal);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());


        // 消息面板
        JPanel messagePanel = new JPanel(new GridLayout(2,1));

        JLabel messageLabel = new JLabel(info1, SwingConstants.CENTER);
        messagePanel.add(messageLabel);

        JLabel messageLabel2 = new JLabel(info2, SwingConstants.CENTER);
        messagePanel.add(messageLabel2);

        this.add(messagePanel, BorderLayout.CENTER);


        // 按钮面板
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        okButton.setPreferredSize(new Dimension(100, 35));
        buttonPanel.add(okButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

        /*// 图标面板
        ImageIcon icon = new ImageIcon("./img/dialog/default.png");
        JLabel iconLabel = new JLabel();// 可替换为实际图标
        iconLabel.setIcon(icon);
        iconLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        this.getContentPane().add(iconLabel); // 将组件添加到面板中*/
    }

}
