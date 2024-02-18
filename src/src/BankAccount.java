package src.src;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;
public class BankAccount implements Serializable{
    //The next 2 lines create instance variables that are needed for the bank account information
    String accountNumber;
    double balance;
    // create arraylist that holds all transactions
    ArrayList<Transaction> transactions = new ArrayList();

    // The next public string method returns an accountNumber
    public String getAccountNumber() {
        return accountNumber;
    }
    // The next public double method returns an account's balance
    public double getBalance() {
        return balance;
    }

    // the next public void method is called on to add transaction information to the transaction arraylist
    public void addToArrayList(Transaction newTransaction) {
        // boolean to for if the info was added or not
        boolean hasBeenAdded = false;
        int transactionSize = transactions.size();
        // for loop that loops through pre existing transactions in the arraylist
        for(int i = 0; i < transactionSize; i++){
            // if statement that checks if the newTransaction is greater than the transaction that the for loop is on, if so it adds it to the arraylist and sets hasBeenAdded to true
            if(transactions.get(i).getTransactionTime().compareTo(newTransaction.getTransactionTime())>0){

                transactions.add(i,newTransaction);
                hasBeenAdded = true;
            }
        }
        // if statement that would only run if the newTransaction time is greater than all previous transactions or if the arraylist is initially empty
        if (hasBeenAdded == false) {
            transactions.add(newTransaction);
        }
    }
    // deposit method that takes in time of the deposit, amount and description
    public void deposit(LocalDateTime transactionTime, double amount, String description) {
        balance += amount;
        Transaction transactionlog = new Transaction(transactionTime, amount, description);
        addToArrayList(transactionlog);
    }
    // deposit method that takes in amount and description
    public void deposit(double amount, String description) {
        balance += amount;
        Transaction transactionlog = new Transaction(amount, description);
        addToArrayList(transactionlog);
    }
    // deposit method that takes in amount, sets description to unavailable
    public void deposit(double amount) {
        balance += amount;
        String description = "Deposit Transaction";
        Transaction transactionlog = new Transaction(amount, description);
        addToArrayList(transactionlog);
    }
    // withdraw method that takes in amount, sets description to unavailable
    public void withdraw(double amount) {
        balance -= amount;
        String description = "Withdraw Transaction";
        Transaction transactionlog = new Transaction(amount, description);
        addToArrayList(transactionlog);
    }
    // withdraw method that takes in time of the withdraw, amount and description
    public void withdraw(LocalDateTime transactionTime, double amount, String description) {
        balance -= amount;
        Transaction transactionlog = new Transaction(transactionTime, amount, description);
        addToArrayList(transactionlog);
    }
    // withdraw method that takes in amount and description
    public void withdraw(double amount, String description) {
        balance -= amount;
        Transaction transactionlog = new Transaction(amount, description);
        addToArrayList(transactionlog);
    }
    // arraylist method that returns transactions that occurred between specific times
    public ArrayList<Transaction> getTransactions(LocalDateTime startTime, LocalDateTime endTime) {
        // create arraylist that will hold all transactions that will be returned at the end
        ArrayList<Transaction> transactionsToReturn = new ArrayList();
        // if statement that fires if theres no starttime but theres and endtime, it will get all transactions that happened up until that endtime
        if((startTime == null)&& (endTime != null)) {
            for(int i = 0; i < transactions.size(); i++) {
                if(transactions.get(i).getTransactionTime().compareTo(endTime)<=0) {
                    transactionsToReturn.add(transactions.get(i));
                }
            }
            // else if statement that fires if theres a starttime but no endtime, it will get all transactions that happened since that starttime and onwards till the latest transaction
        } else if ((startTime!=null)&&(endTime == null)) {
            for(int i = 0; i < transactions.size(); i++) {
                if(transactions.get(i).getTransactionTime().compareTo(startTime)>=0) {
                    transactionsToReturn.add(transactions.get(i));
                }
            }
            // else if statement that fires if theres no starttime or end time, it will get all transactions that occured no matter the time
        } else if ((startTime == null) && (endTime == null)) {
            for(int i = 0; i < transactions.size(); i++) {
                transactionsToReturn.add(transactions.get(i));
            }
            // else statement that fires of there is a starttime and an endtime, it will get all transactions that occurred in that given time period
        } else {
            for(int i = 0; i < transactions.size(); i++) {
                if((transactions.get(i).getTransactionTime().compareTo(startTime)>=0)&&(transactions.get(i).getTransactionTime().compareTo(endTime)<=0)) {
                    transactionsToReturn.add(transactions.get(i));
                }
            }
        }
        // return the transactions that occured in the time period provided
        return transactionsToReturn;
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
    }

}
