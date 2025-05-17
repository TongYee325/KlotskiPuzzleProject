package frame.dialog;

import frame.GameFrame;
import gamestate.MyGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DefaultDialog extends JDialog {
    public DefaultDialog(Frame owner, String title, boolean modal, String info1, String info2) {
        //用户登录提示框
        super(owner, title,modal);
        setSize(300, 200);
        initial(owner, info1, info2);

    }

    public DefaultDialog(Frame owner, String title, boolean modal, String info1, String info2, int width, int height) {
        //存档提示框
        super(owner, title,modal);
        setSize(width, height);
        initial(owner, info1, info2);

    }

    private void initial(Frame owner, String info1, String info2) {

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
    }


    public DefaultDialog(Frame owner, String title, boolean modal, String mainMessage,
                             JPanel contentPanel, String buttonText, Dimension size) {
        //胜利提示框
        super(owner, title, modal);
        setSize(size);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 245, 245)); // 默认背景色



            // 2. 主消息面板（可选）
        if (mainMessage != null) {
                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(new Color(245, 245, 245));
                JLabel titleLabel = new JLabel(mainMessage, SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
                titleLabel.setForeground(new Color(30, 30, 30));
                titlePanel.add(titleLabel);
                add(titlePanel, BorderLayout.NORTH);
        }

            // 3. 中间内容面板（可选，如统计信息）
        if (contentPanel != null) {
                contentPanel.setBackground(new Color(245, 245, 245));
                add(contentPanel, BorderLayout.CENTER);
        }

            // 4. 按钮面板（必选）
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton confirmBtn = new JButton(buttonText == null ? "conform" : buttonText);
        confirmBtn.addActionListener(e->{
            ((GameFrame) owner).getRlevel().getrGameState().startLevel(1);
        });
        confirmBtn.setBackground(new Color(0, 120, 215));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmBtn.setPreferredSize(new Dimension(100, 35));
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
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
