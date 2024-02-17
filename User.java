public class User {
    // declare protected attributes
    protected String username, email;
    protected double balance;

    // uses the parameters to create an object pertaining to a bank account
    public User(String username, double balance, String email) {
        this.username = username;
        this.balance = balance;
        this.email = email;
    }

    // method to deposit an amount towards the total balance
    public void deposit(double depositAmount) {
        this.balance += depositAmount; // just adds the argument within the method towards the balance attribute in the object
        System.out.println("Deposit of " + depositAmount + " made. New balance is " + this.balance);
    }

    // method to withdraw an amount from the total balance
    public void withdraw(double withdrawalAmount) {
        if (this.balance - withdrawalAmount < 0) { // essentially tests if the total balance is less than the desired amount to withdraw
            System.out.println("Only " + this.balance + " available. Withdrawal not processed.");
        }

        else { // If the withdrawal is less than the total balance, then subtract that withdrawal amount from the total balance
            this.balance -= withdrawalAmount;
            System.out.println("Withdrawal of " + withdrawalAmount + " processed. Remaining balance = " + this.balance);
        }
    }

    // getter method to help display username
    public String getUsername() {
        return username;
    }

    // getter method for balance
    public double getBalance() {
        return balance;
    }

    // getter method for email
    public String getEmail() {
        return email;
    }
}
