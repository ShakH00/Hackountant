package src.src;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen() {
        JButton budgetButton = new JButton("Budgeting");
        JButton investButton = new JButton("Investing");
        JButton learnButton = new JButton("Learning");

        // Add the buttons to the frame
        add(budgetButton);
        add(investButton);
        add(learnButton);

        // Set the layout and size of the frame
        setLayout(new FlowLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}