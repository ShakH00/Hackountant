package src.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MainScreen extends JFrame implements WindowListener{
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
            Chequing = new BankAccount("Chequing",0);
            initializeBalance = new InitializeAccount(Chequing);
            initializeBalance.addWindowListener(this);
            initializeBalance.setVisible(true);
        } else{
            Chequing = deSerializeAccounts();
        }

        JButton budgetButton = new JButton("Budgeting");
        JButton investButton = new JButton("Investing");
        JButton learnButton = new JButton("Learning");

        // Add the buttons to the frame
        add(budgetButton);
        add(investButton);
        add(learnButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        investButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openInvestments();
            }
        });
    }

    public void openBudget(){
        budgetWindow = new BudgetGUI();
        budgetWindow.addWindowListener(this);
        budgetWindow.setVisible(true);
    }

    public void openInvestments(){
        stocksWindow = new StockMarket(Chequing);
        stocksWindow.addWindowListener(this);
        stocksWindow.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e){
        serializeAccounts(Chequing);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}