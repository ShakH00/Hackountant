package src.src;
//imports
import java.io.Serializable;
import java.time.LocalDateTime;
//class used for bankaccount info
public class Transaction implements Serializable{

    LocalDateTime transactionTime;
    double amount;
    String description;

    public Transaction(LocalDateTime transactionTime, double amount, String description){
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.description = description;
    }

    public Transaction(double amount, String description){
        this.transactionTime = LocalDateTime.now();
        this.amount = amount;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getTransactionTime(){
        return transactionTime;
    }

    public double getAmount(){
        return amount;
    }

    public String toString() {
        return this.getClass().toString() + ":" + this.transactionTime.toString();
    }
}
