package src.src;

import java.io.*;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BankAccount> accounts;
    private double totalBalance;
    private List<Expense> expenses; // Changed to list of Expense objects

    public Budget() {
        expenses = new ArrayList<>();
        accounts = new ArrayList<>();
        totalBalance = 0.0;
    }
    public void addAccount(BankAccount account) {
        accounts.add(account);
        totalBalance += account.getBalance();
    }

    public void removeAccount(BankAccount account) {
        accounts.remove(account);
        totalBalance -= account.getBalance();
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void suggestInvestment(int balance, int expenses) {
        if (balance - expenses > 0) {
            System.out.println("Investment is suggested.");
        } else if(balance - expenses == 0){
            System.out.println("Investment will cause you to go into deficit.");
        } else {
            System.out.println("Budget deficit. Cut down expenses.");
        }
    }

    // Modified to add an expense with date and time
    public void addExpense(String type, double amount, LocalDateTime dateTime) {
        expenses.add(new Expense(type, amount, dateTime));
    }

    // Removing an expense now requires identifying the specific expense to remove
    public void removeExpense(String type, LocalDateTime dateTime) {
        expenses.removeIf(expense -> expense.getType().equals(type) && expense.getDateTime().equals(dateTime));
    }

    // Calculating total expenses
    public double totalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // Getter for expenses
    public List<Expense> getExpenses() {
        return expenses;
    }

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

}

class Expense implements Serializable {
    private String type;
    private double amount;
    private LocalDateTime dateTime;

    public Expense(String type, double amount, LocalDateTime dateTime) {
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
