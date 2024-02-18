package src.src;

import java.io.Serializable;
import java.util.*;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BankAccount> accounts;
    private double totalBalance;
    private HashMap<String, Double> expenses;

    public Budget() {
        expenses = new HashMap<>();
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
        } else {
            System.out.println("Budget deficit. Cut down expenses.");
        }
    }

    public void addExpense(String type, double amount) {
        if(expenses.containsKey(type)) {
            expenses.put(type, expenses.get(type) + amount);
        } else {
            expenses.put(type, amount);
        }
    }

    public void removeExpense(String type) {
        expenses.remove(type);
    }

    public double totalExpenses() {
        double total = 0;
        for(double amount : expenses.values()) {
            total += amount;
        }
        return total;
    }
}
