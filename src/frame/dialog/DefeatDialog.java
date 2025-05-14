package frame.dialog;

import javax.swing.*;
import java.awt.*;

public class DefeatDialog extends JDialog {
    public DefeatDialog(Frame owner) {
        super(owner, "Game Over", true);
        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Icon panel
        JPanel iconPanel = new JPanel();
        JLabel iconLabel = new JLabel(new ImageIcon("resources/defeat_icon.png")); // Replace with actual icon
        iconPanel.add(iconLabel);
        add(iconPanel, BorderLayout.NORTH);

        // Message panel
        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("Time has run out!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        okButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        okButton.setPreferredSize(new Dimension(100, 35));
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}