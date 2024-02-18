package src.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

public class BankingWindow extends JFrame implements WindowListener {
    private JPanel contentPane;
    private BankAccount Chequing;
    protected  JTextField txtField;
    private JLabel errorTxt;
    ManualPayInput manualPay;

    public BankingWindow(BankAccount account){
        Chequing = account;
        setTitle("Edit Income Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0,0,400,250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel amtTxt = new JLabel("Biweekly paycheque:");
        amtTxt.setBounds(150,25,150,50);
        contentPane.add(amtTxt);

        errorTxt = new JLabel("");
        errorTxt.setBounds(125,125,500,100);
        contentPane.add(errorTxt);

        txtField = new JTextField();
        txtField.setBounds(150,75,100,20);
        txtField.setColumns(10);
        contentPane.add(txtField);

        JButton setPaychequeBtn = new JButton("Set Paycheque");
        setPaychequeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBiweeklyPay();
            }
        });
        setPaychequeBtn.setBounds(50,125,125,20);
        contentPane.add(setPaychequeBtn);

        JButton manualInputBtn = new JButton("Manual input");
        manualInputBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setManualPayInput();
            }
        });
        manualInputBtn.setBounds(225,125,125,20);
        contentPane.add(manualInputBtn);

        JButton skip2weeksBtn = new JButton("Time skip");
        skip2weeksBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timeSkip();
            }
        });
        skip2weeksBtn.setBounds(10,15,100,20);
        contentPane.add(skip2weeksBtn);
    }

    public void timeSkip(){
        boolean manualOrAuto = Chequing.isManualInput();
        if(manualOrAuto){
            manualPay = new ManualPayInput(Chequing);
            manualPay.addWindowListener(this);
            manualPay.setVisible(true);
        } else{
            double pay = Chequing.getBiweeklyPay();
            Chequing.deposit(pay, "BiWeekly paycheque");
        }
    }

    public void setBiweeklyPay(){
        errorTxt.setText("");
        try{
            double amt = Double.valueOf(txtField.getText());
            Chequing.setBiweeklyPay(amt);
            Chequing.setManualInput(false);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } catch(NumberFormatException e) {
            errorTxt.setForeground(Color.RED);
            errorTxt.setText("That is not a number!");
        }
    }

    public void setManualPayInput(){
        errorTxt.setText("");
        Chequing.setBiweeklyPay(0);
        Chequing.setManualInput(true);
        errorTxt.setForeground(Color.BLACK);
        try{
            errorTxt.setText("Every 2 weeks you will be asked how much you earnt!");
            Thread.sleep(500);
            errorTxt.setText("");
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            errorTxt.setText("");
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
