package src.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MainScreen extends JFrame implements WindowListener{
    private BudgetGUI budgetWindow;
    private StockMarket stocksWindow;
    private Learning learnWindow;
    private BankingWindow paySelect;
    BankAccount Chequing;
    InitializeAccount initializeBalance;
    ManualPayInput manualPay;
    private JPanel contentPane;
    private JLabel displayBal;


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
        if(Chequing.isNewPayDay()==true){
            if(Chequing.isManualInput()==true){
                manualPay = new ManualPayInput(Chequing);
                manualPay.addWindowListener(this);
                manualPay.setVisible(true);
            } else{
                double pay = Chequing.getBiweeklyPay();
                Chequing.deposit(pay, "BiWeekly paycheque");
            }

        }
        setBounds(0,0,400,250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        JButton budgetButton = new JButton("Budgeting");
        budgetButton.setBounds(75, 100, 125, 20);
        contentPane.add(budgetButton);
        JButton investButton = new JButton("Investing");
        investButton.setBounds(200,100,125,20);
        contentPane.add(investButton);
        JButton learnButton = new JButton("Learning");
        learnButton.setBounds(75,125,125,20);
        contentPane.add(learnButton);
        JButton paySelButton = new JButton("Edit Paycheque");
        paySelButton.setBounds(200,125,125,20);
        contentPane.add(paySelButton);
        JLabel balTxt = new JLabel("Balance: ");
        balTxt.setBounds(75,25,100,50);
        contentPane.add(balTxt);


        displayBal = new JLabel("");
        displayBal.setBounds(150,25,100,50);
        contentPane.add(displayBal);
        displayBal.setText(String.valueOf(Chequing.getBalance()));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the layout and size of the frame

        budgetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openBudget();
            }
        });

        paySelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openPaySelect();
            }
        });
        investButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openInvestments();
            }
        });

        learnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLesson();
            }
        });
    }

    public void openPaySelect(){
        paySelect = new BankingWindow(Chequing);
        paySelect.addWindowListener(this);
        paySelect.setVisible(true);
    }

    public void openLesson(){
        learnWindow = new Learning();
        learnWindow.addWindowListener(this);
        learnWindow.setVisible(true);
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
        displayBal.setText(String.valueOf(Chequing.getBalance()));

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