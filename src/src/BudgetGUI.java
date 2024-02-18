package src.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BudgetGUI extends JFrame {
    private Budget budget;
    private JTextField typeField, amountField;
    private JTextArea displayArea;

    public BudgetGUI() {
        budget = new Budget();

        setLayout(new FlowLayout());

        typeField = new JTextField(10);
        amountField = new JTextField(10);
        displayArea = new JTextArea(5, 20);

        add(new JLabel("Type:"));
        add(typeField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(new JButton(new AbstractAction("Add") {
            public void actionPerformed(ActionEvent e) {
                budget.addExpense(typeField.getText(), Double.parseDouble(amountField.getText()));
                displayArea.setText("Total: " + budget.totalExpenses());
            }
        }));
        add(new JButton(new AbstractAction("Remove") {
            public void actionPerformed(ActionEvent e) {
                budget.removeExpense(typeField.getText());
                displayArea.setText("Total: " + budget.totalExpenses());
            }
        }));
        add(displayArea);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BudgetGUI();
    }
}
