package src.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MainScreen extends JFrame {
    private BudgetGUI budgetWindow;
    private StockMarket stocksWindow;
    BankAccount Chequing;
    InitializeAccount initializeBalance;

    public void serializeAccounts(BankAccount account){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("ChequingAccount.txt"));
            outputStream.writeObject(account);
            outputStream.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public BankAccount deSerializeAccounts(){
        BankAccount accountToReturn = null;
        try{
            FileInputStream inputStream = new FileInputStream("ChequingAccount.txt");
            ObjectInputStream reader = new ObjectInputStream(inputStream);
            accountToReturn = (BankAccount)reader.readObject();
        } catch(IOException e){
            System.out.println(e);
        } catch(ClassNotFoundException e){
            System.out.println(e);
        }
        return accountToReturn;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainScreen frame = new MainScreen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MainScreen() {
        if(deSerializeAccounts()==null){
            Chequing = new BankAccount("Chequing");
            initializeBalance = new InitializeAccount(Chequing);
            initializeBalance.setVisible(true);
        }

        JButton budgetButton = new JButton("Budgeting");
        JButton investButton = new JButton("Investing");
        JButton learnButton = new JButton("Learning");

        // Add the buttons to the frame
        add(budgetButton);
        add(investButton);
        add(learnButton);

        // Set the layout and size of the frame
        setLayout(new FlowLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        budgetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openBudget();
            }
        });
    }

    public void openBudget(){
        budgetWindow = new BudgetGUI();
        budgetWindow.setVisible(true);
    }

    /*public void openInvestments(){
        stocksWindow = new StockMarket();
        stocksWindow.addW
    }*/
}