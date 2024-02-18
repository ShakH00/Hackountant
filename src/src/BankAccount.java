package src.src;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class BankAccount implements Serializable{
    String accountNumber;
    double balance;
    boolean manualInput;
    double biweeklyPay;
    LocalDate lastPayDay;

    // The next public string method returns an accountNumber
    public String getAccountNumber() {
        return accountNumber;
    }
    // The next public double method returns an account's balance
    public double getBalance() {
        return balance;
    }

    public double getBiweeklyPay(){
        return biweeklyPay;
    }

    public boolean isManualInput(){
        return manualInput;
    }

    public double monthlyPay(){
        double monthly = biweeklyPay*2;
        return monthly;
    }

    public void setBiweeklyPay(double pay){
        biweeklyPay = pay;
    }

    public void setManualInput(boolean choice){
        manualInput = choice;
    }

    public boolean isNewPayDay(){
        LocalDate today = LocalDate.now();
        if(DAYS.between(lastPayDay,today) >= 14){
            lastPayDay = today;
            return true;
        }
        return false;
    }



    // deposit method that takes in amount and description
    public void deposit(double amount, String description) {
        balance += amount;
    }
    // deposit method that takes in amount, sets description to unavailable
    public void deposit(double amount) {
        balance += amount;

    }
    // withdraw method that takes in amount, sets description to unavailable
    public void withdraw(double amount) {
        balance -= amount;

    }

    // withdraw method that takes in amount and description
    public void withdraw(double amount, String description) {
        balance -= amount;
    }


    // The next public boolean method checks if a bank account's balance is in debt (less than $0 in it) and if it is, returns true, if it is not, it returns false
    public boolean isInDebt() {
        if (balance < 0) {
            return(true);
        } else {
            return(false);
        }
    }
    // The next constructor creates a bank account with an account number
    public BankAccount(String anumber) {
        accountNumber = anumber;
    }
    // The next constructor creates a bank account with an account number and an initial balance
    public BankAccount(String anumber, double ibal) {
        accountNumber = anumber;
        balance = ibal;
        manualInput = false;
        biweeklyPay = 0;
        lastPayDay = LocalDate.now();
    }

}
