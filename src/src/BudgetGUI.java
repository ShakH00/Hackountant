package src.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BudgetGUI extends JFrame {
    private Budget budget;
    private JTextField typeField, amountField, dateTimeField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel, balanceLabel, dateTimeLabel, monthlyLabel, monthlySpending; // Labels for total expenses, total balance, and current date/time
    private SimpleDateFormat dateTimeFormat;
    BankAccount Chequing;

    public void serializeBudget(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Budget deserializeBudget(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Budget) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Budget(); // Return a new instance if deserialization fails
        }
    }

    public BudgetGUI(BankAccount account) {
        budget = deserializeBudget("budget_state.txt");

        Chequing = account;
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Example date and time format

        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridLayout(2, 1)); // Using GridLayout to organize components
        JPanel fieldsPanel = new JPanel(new FlowLayout()); // Panel for input fields and buttons
        JPanel dateTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for displaying current date/time

        typeField = new JTextField(10);
        amountField = new JTextField(10);
        dateTimeField = new JTextField(15); // For combined date and time input

        fieldsPanel.add(new JLabel("Type:"));
        fieldsPanel.add(typeField);
        fieldsPanel.add(new JLabel("Amount:"));
        fieldsPanel.add(amountField);
        fieldsPanel.add(new JLabel("Date & Time (yyyy-MM-dd HH:mm):"));
        fieldsPanel.add(dateTimeField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addExpense());

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeSelectedExpense());

        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> suggestInvestment());

        JButton saveButton = new JButton("Save Budget");
        saveButton.addActionListener(e -> saveBudgetState());

        JButton resetButton = new JButton("Reset Expenses");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetExpenses();
            }
        });

        fieldsPanel.add(addButton);
        fieldsPanel.add(removeButton);
        fieldsPanel.add(finishButton);
        fieldsPanel.add(saveButton);
        fieldsPanel.add(resetButton);

        // Current date and time display
        dateTimeLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dateTimePanel.add(dateTimeLabel);

        inputPanel.add(fieldsPanel);
        inputPanel.add(dateTimePanel);

        // Table setup
        String[] columnNames = {"Type", "Date", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        for(Expense element : budget.getExpenses()){
            tableModel.addRow(new Object[]{element.getType(), element.getDateTime(), String.valueOf(element.getAmount())});
        }

        // Total expenses and balance display setup
        JPanel totalPanel = new JPanel(new FlowLayout());
        totalLabel = new JLabel("Total Expenses: $0.00");
        budget.addAccount(Chequing);
        balanceLabel = new JLabel("Total Balance: $" + budget.getTotalBalance()); // Display total balance
        monthlyLabel = new JLabel("Monthly income: N/A");
        monthlySpending = new JLabel("Monthly Net Income: N/A");

        if(Chequing.isManualInput()==true){
            double estimatedPay = Chequing.lastManualPay()*2;
            monthlyLabel.setText("Monthly income: $"+estimatedPay);
        } else{
            monthlyLabel.setText("Monthly income: $"+Chequing.monthlyPay());
        }

        totalPanel.add(totalLabel);
        totalPanel.add(monthlyLabel);
        totalPanel.add(monthlySpending);
        totalPanel.add(balanceLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH); // Add the total display at the bottom

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void resetExpenses(){
        tableModel.setRowCount(0);
        budget.removeAllExpenses();
        budget.resetExpenses();
        updateTotal();
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
        balanceLabel.setText("Total Balance: $" + budget.getTotalBalance()); // Update balance display
        totalLabel.setText("Total expenses: "+String.valueOf(total));
        if(Chequing.isManualInput() == true){
            double calculateExpenses = (Chequing.lastManualPay()*2)- budget.getTotalExpenses();
            monthlySpending.setText("Monthly Net Income: $"+calculateExpenses);
        } else{
            double calculateExpenses = Chequing.monthlyPay() - budget.getTotalExpenses();
            monthlySpending.setText("Monthly Net Income: $"+calculateExpenses);
        }


    }

    private void suggestInvestment() {
        double totalExpenses = budget.getTotalExpenses();
        double monthlyPay = 0;
        if(Chequing.isManualInput()){
            monthlyPay = Chequing.lastManualPay()*2;
        } else{
            monthlyPay = Chequing.monthlyPay();
        }
        double totalBalance = budget.getTotalBalance();
        String message = "";
        if ((monthlyPay-totalExpenses<=0) && (totalBalance+monthlyPay) - totalExpenses > 0) {
            message = "Investment is suggested.";
        } else if((monthlyPay-totalExpenses<=0) && (totalBalance+monthlyPay) - totalExpenses < 0) {
            message = "You are losing money every month! Cut down expenses.";
        } else if((totalBalance+monthlyPay) - totalExpenses == 0){
            message = "Investment will cause deficit.";
        } else if(monthlyPay-totalExpenses > 100){
            message = "You save money every month! Investment is highly suggested!";
        } else if((totalBalance+monthlyPay) - totalExpenses == 0){
            message = "Budget deficit, you spend more than you make a month. Cut down expenses.";
        }
        JOptionPane.showMessageDialog(this, message);
    }

    private void saveBudgetState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("budget_state.txt"))) {
            oos.writeObject(budget);
            JOptionPane.showMessageDialog(this, "Budget state saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save budget state.");
        }
    }

    private void loadBudgetState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("budget_state.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof Budget) {
                budget = (Budget) obj;
                // Now update the GUI based on the loaded budget
                updateGUIFromBudget();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load budget state.");
        }
    }

    private void updateGUIFromBudget() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Populate table with expenses from the loaded budget
        for (Expense expense : budget.getExpenses()) {
            tableModel.addRow(new Object[]{
                    expense.getType(),
                    dateTimeFormat.format(Date.from(expense.getDateTime().atZone(java.time.ZoneId.systemDefault()).toInstant())),
                    String.valueOf(expense.getAmount())
            });
        }

        // Update total and balance labels
        updateTotal();
    }
}
