package frame.dialog;

import gamestate.MyGameState;
import frame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VictoryDialog extends JDialog {
    public VictoryDialog(GameFrame parentFrame, long elapsedTime) {
        super(parentFrame, "Victory!", true);
        setSize(450, 250);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 245, 245));

        MyGameState gameState = parentFrame.getRlevel().getrGameState();
        int totalSteps = gameState.getMyLogSystem().getTotalSteps().size();
        long totalTime = parentFrame.getElapsedTime();

        // Victory title (top)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("You Win!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));
        titlePanel.add(titleLabel);

        // Statistics (middle)
        JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
        infoPanel.setBackground(new Color(245, 245, 245));

        // Total steps panel (center)
        JPanel stepsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel stepsLabel = new JLabel("Total Steps:");
        stepsLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        JTextField stepsField = new JTextField(String.valueOf(totalSteps), 15);
        stepsField.setEditable(false);
        stepsField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        stepsField.setBackground(Color.WHITE);
        stepsField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        stepsPanel.add(stepsLabel);
        stepsPanel.add(stepsField);
        infoPanel.add(stepsPanel, BorderLayout.NORTH);

        // Total time panel (center)
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel timeLabel = new JLabel("Total Time:");
        timeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        JTextField timeField = new JTextField(formatTime(totalTime), 15);
        timeField.setEditable(false);
        timeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        timeField.setBackground(Color.WHITE);
        timeField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        timePanel.add(timeLabel);
        timePanel.add(timeField);
        infoPanel.add(timePanel, BorderLayout.CENTER);

        // Remaining time panel (only for timed mode)
        if (gameState.isTimedMode()) {
            long remainingTime = gameState.getRemainingTime();
            JPanel remainingTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel remainingTimeLabel = new JLabel("Remaining Time:");
            remainingTimeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
            JTextField remainingTimeField = new JTextField(formatTime(remainingTime), 10);
            remainingTimeField.setEditable(false);
            remainingTimeField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            remainingTimeField.setBackground(Color.WHITE);
            remainingTimeField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
            remainingTimePanel.add(remainingTimeLabel);
            remainingTimePanel.add(remainingTimeField);
            infoPanel.add(remainingTimePanel, BorderLayout.SOUTH);
        }

        // Confirm button (bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(e -> dispose());
        buttonPanel.add(confirmBtn);
        confirmBtn.setBackground(new Color(0, 120, 215));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        confirmBtn.setPreferredSize(new Dimension(80, 35));
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

    public VictoryDialog(GameFrame rFrame) {
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
            return String.format("%02d seconds", seconds);
        }
    }
}