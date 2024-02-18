package src.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class ManualPayInput extends JFrame {
    private JPanel contentPane;
    private BankAccount Chequing;
    protected  JTextField txtField;
    private JLabel errorTxt;

    public ManualPayInput(BankAccount account){
        Chequing = account;
        setTitle("Input Manual Pay");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBounds(0,0,400,250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel enterAmtTxt = new JLabel("PayCheque for last 2 weeks:");
        enterAmtTxt.setBounds(75,25,350,50);
        contentPane.add(enterAmtTxt);

        errorTxt = new JLabel("");
        errorTxt.setBounds(150,125,500,100);
        contentPane.add(errorTxt);

        txtField = new JTextField();
        txtField.setBounds(150,75,100,20);
        txtField.setColumns(10);
        contentPane.add(txtField);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manualDeposit();
            }
        });
        confirmBtn.setBounds(150,125,100,20);
        contentPane.add(confirmBtn);

    }


    public void manualDeposit(){
        errorTxt.setForeground(Color.BLACK);
        errorTxt.setText("");
        try{
            double amt = Double.valueOf(txtField.getText());
            Chequing.deposit(amt, "Manual biweekly paycheque");
            Chequing.setLastManualPay(amt);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } catch(NumberFormatException e){
            errorTxt.setForeground(Color.RED);
            errorTxt.setText("That is not a number!");
        }
    }
}
