package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is to create basic JComponent.
 */
public class FrameUtil {
    public static int[] getScreenDimensions() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return new int[]{screenSize.width, screenSize.height};
    }

    public static JLabel createJLabel(JFrame frame, Point location, int width, int height, String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setSize(width, height);
        jLabel.setLocation(location);
        frame.add(jLabel);
        return jLabel;
    }

    public static JLabel createJLabel(JFrame frame, String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        frame.add(label);
        return label;
    }

    public static JTextField createJTextField(JFrame frame, Point location, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(width, height);
        jTextField.setLocation(location);
        frame.add(jTextField);
        return jTextField;
    }

    public static JButton createButton(JFrame frame, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        frame.add(button);
        return button;
    }

    public static JPasswordField createJPasswordField(JFrame frame, Point location, int width, int height) {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setSize(width, height);
        jPasswordField.setLocation(location);
        frame.add(jPasswordField);
        return jPasswordField;
    }


}
