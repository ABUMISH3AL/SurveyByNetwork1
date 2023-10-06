import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComponentBuilder {

    public static JButton CreateAButton(String text, int width, int height, int xPos, int yPos, ActionListener listener, SpringLayout layout, JFrame frame) {
        JButton myNewButton = new JButton(text);
        myNewButton.addActionListener(listener);
        myNewButton.setPreferredSize(new Dimension(width, height));
        layout.putConstraint(SpringLayout.WEST, myNewButton, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myNewButton, yPos, SpringLayout.NORTH, frame);
        frame.add(myNewButton);
        return myNewButton;
    }

    public static JTextField CreateATextFieldUnEditable(int width, int height, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JTextField myTextField = new JTextField();
        myTextField.setPreferredSize(new Dimension(width, height));
        myTextField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        myTextField.setBackground(Color.WHITE);
        myTextField.setEditable(false);
        layout.putConstraint(SpringLayout.WEST, myTextField, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myTextField, yPos, SpringLayout.NORTH, frame);
        frame.add(myTextField);
        return myTextField;
    }

    public static JTextField CreateATextFieldEditable(int width, int height, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JTextField myTextField = new JTextField();
        myTextField.setPreferredSize(new Dimension(width, height));
        layout.putConstraint(SpringLayout.WEST, myTextField, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myTextField, yPos, SpringLayout.NORTH, frame);
        frame.add(myTextField);
        return myTextField;
    }

    public static JTextArea CreateATextAreaUnEditable(int width, int height, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JTextArea myTextArea = new JTextArea();
        myTextArea.setPreferredSize(new Dimension(width, height));
        myTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        myTextArea.setForeground(Color.black);
        myTextArea.setEditable(false);
        layout.putConstraint(SpringLayout.WEST, myTextArea, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myTextArea, yPos, SpringLayout.NORTH, frame);
        frame.add(myTextArea);
        return myTextArea;
    }

    public static JTextArea CreateATextAreaEditable(int width, int height, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JTextArea myTextArea = new JTextArea();
        myTextArea.setPreferredSize(new Dimension(width, height));
        myTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        myTextArea.setForeground(Color.black);
        layout.putConstraint(SpringLayout.WEST, myTextArea, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myTextArea, yPos, SpringLayout.NORTH, frame);
        frame.add(myTextArea);
        return myTextArea;
    }

    public static JLabel CreateALabel(String text, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JLabel myLabel = new JLabel(text);
        layout.putConstraint(SpringLayout.WEST, myLabel, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myLabel, yPos, SpringLayout.NORTH, frame);
        frame.add(myLabel);
        return myLabel;
    }

    public static JTable CreateATable(int width, int height, int xPos, int yPos, SpringLayout layout, JFrame frame) {
        JTable myJTable = new JTable();
        myJTable.setPreferredSize(new Dimension(width, height));
        myJTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        layout.putConstraint(SpringLayout.WEST, myJTable, xPos, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, myJTable, yPos, SpringLayout.NORTH, frame);
        frame.add(myJTable);
        return myJTable;
    }

}
