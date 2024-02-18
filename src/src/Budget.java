package src.src;

import java.io.Serializable;
import java.util.*;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BankAccount> accounts;
    private double totalBalance;

    public Budget() {
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

}
