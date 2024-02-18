package src.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class BudgetGUI extends JFrame {
    private Budget budget;
    private JTextField typeField, amountField, dateTimeField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel; // Label to display the total expenses
    private SimpleDateFormat dateTimeFormat;

    public BudgetGUI() {
        budget = new Budget();
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Example date and time format

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        typeField = new JTextField(10);
        amountField = new JTextField(10);
        dateTimeField = new JTextField(15); // For combined date and time input

        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Date & Time (yyyy-MM-dd HH:mm):"));
        inputPanel.add(dateTimeField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addExpense());

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeSelectedExpense());

        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        // Table setup
        String[] columnNames = {"Type", "Date", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Total expenses display setup
        JPanel totalPanel = new JPanel(new FlowLayout());
        totalLabel = new JLabel("Total: $0.00");
        totalPanel.add(totalLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH); // Add the total display at the bottom

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addExpense() {
        try {
            Date date = dateTimeFormat.parse(dateTimeField.getText());
            String type = typeField.getText();
            double amount = Double.parseDouble(amountField.getText());
            budget.addExpense(type, amount, date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());

            // Update table model
            tableModel.addRow(new Object[]{type, dateTimeField.getText(), String.valueOf(amount)});

            updateTotal(); // Recalculate and update the total expenses

            JOptionPane.showMessageDialog(this, "Expense added successfully!");
        } catch (ParseException parseException) {
            JOptionPane.showMessageDialog(this, "Invalid date/time format!");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!");
        }
    }

    private void removeSelectedExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Extract the type and amount from the selected row
            String type = (String) tableModel.getValueAt(selectedRow, 0);
            double amount = Double.parseDouble((String) tableModel.getValueAt(selectedRow, 2));

            // Parse the date and time from the string in the table
            LocalDateTime dateTime = null;
            try {
                Date date = dateTimeFormat.parse((String) tableModel.getValueAt(selectedRow, 1));
                dateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Error parsing the date/time for the selected expense.");
                return;
            }

            // Remove the expense from the budget
            budget.removeExpense(type, dateTime);

            // Remove the row from the table model
            tableModel.removeRow(selectedRow);

            // Update the total expenses display
            updateTotal();
            JOptionPane.showMessageDialog(this, "Expense removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to remove.");
        }
    }

    private void updateTotal() {
        double total = 0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            double amount = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
            total += amount;
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    public static void main(String[] args) {
        new BudgetGUI();
    }
}
