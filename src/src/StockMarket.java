package src.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class StockMarket extends JFrame implements WindowListener {
    private JPanel NASDAQ;
    private JComboBox companySelect;
    private JTextField buyField;
    private JTextField sellField;
    private JButton buyBtn;
    private JButton sellBtn;
    private JLabel sellText;
    private JLabel buyText;
    private JLabel costPrShare;
    private JLabel ownedSharesTxt;
    private JLabel balTxt;
    private JLabel sharePriceTxt;
    private JLabel ownedTxt;
    private JLabel errorTxt;
    stockAccount googleShares;
    stockAccount microsoftShares;
    stockAccount teslaShares;
    stockAccount costcoShares;
    stockAccount nvidiaShares;
    private String companyChosen;
    BankAccount bankAccount;

    public void serializeShares(String corpName, stockAccount sharesToSerialize){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(corpName.toLowerCase()+"Shares.txt"));
            outputStream.writeObject(sharesToSerialize);
            outputStream.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public stockAccount deSerializeShares(String corpName){
        stockAccount acc = null;
        try{
            FileInputStream inputStream = new FileInputStream(corpName.toLowerCase()+"Shares.txt");
            ObjectInputStream reader = new ObjectInputStream(inputStream);

            acc = (stockAccount)reader.readObject();
        } catch (IOException e){
            System.out.println(e);
        } catch(ClassNotFoundException e){
            System.out.println(e);
        }
        return acc;
    }
    public String costPerShare(String companyName){
        String price = "not found";
        try{
            URL url = new URL("https://www.google.com/finance/quote/"+companyName+":NASDAQ");
            URLConnection urlConnect = url.openConnection();
            InputStreamReader inputStrm = new InputStreamReader(urlConnect.getInputStream());
            BufferedReader buff = new BufferedReader(inputStrm);
            String line = buff.readLine();
            while(line != null){
                if(line.contains("[\""+companyName+"\",")){
                    int target = line.indexOf("[\""+companyName+"\",");
                    int deci = line.indexOf(".", target);
                    int start = deci;
                    while(line.charAt(start) != '\"'){
                        start--;
                    }
                    price = line.substring(start+1,deci+3);
                }
                line = buff.readLine();
            }
            price = price.replaceAll("[^0-9.]","");
            return price;
        } catch(IOException e){
            return("INVALID URL");
        }
    }
    public StockMarket(BankAccount account){
        companyChosen = "GOOGL";
        balTxt.setText(String.valueOf(account.getBalance()));
        bankAccount = account;
        setAlwaysOnTop(true);
        setBounds(0,0,700,500);
        setContentPane(NASDAQ);
        setTitle("Learn to Invest");
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        if(deSerializeShares("GOOGL")==null){
            googleShares = new stockAccount("GOOGL",0);
            serializeShares("GOOGL",googleShares);
        } else{
            googleShares = deSerializeShares("GOOGL");
        }

        if(deSerializeShares("MSFT")==null){
            microsoftShares = new stockAccount("MSFT",0);
            serializeShares("MSFT",microsoftShares);
        } else{
            microsoftShares = deSerializeShares("MSFT");
        }

        if(deSerializeShares("TSLA")==null){
            teslaShares = new stockAccount("TSLA",0);
            serializeShares("TSLA",teslaShares);
        } else{
            teslaShares = deSerializeShares("TSLA");
        }

        if(deSerializeShares("COST")==null){
            costcoShares = new stockAccount("COST",0);
            serializeShares("COST",costcoShares);
        } else{
            costcoShares = deSerializeShares("COST");
        }

        if(deSerializeShares("NVDA")==null){
            nvidiaShares = new stockAccount("NVDA",0);
            serializeShares("NVDA",nvidiaShares);
        } else{
            nvidiaShares = deSerializeShares("NVDA");
        }
        ownedTxt.setText(String.valueOf(googleShares.getSharesOwned()));
        sharePriceTxt.setText(String.valueOf(googleShares.costPerShare()));

        companySelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(companySelect.getSelectedItem().equals("GOOGL")){
                    companyChosen = "GOOGL";
                    ownedTxt.setText(String.valueOf(googleShares.getSharesOwned()));
                    sharePriceTxt.setText(googleShares.costPerShare());
                } else if(companySelect.getSelectedItem().equals("MSFT")){
                    companyChosen = "MSFT";
                    ownedTxt.setText(String.valueOf(microsoftShares.getSharesOwned()));
                    sharePriceTxt.setText(microsoftShares.costPerShare());
                } else if(companySelect.getSelectedItem().equals("TSLA")){
                    companyChosen = "TSLA";
                    ownedTxt.setText(String.valueOf(teslaShares.getSharesOwned()));
                    sharePriceTxt.setText(teslaShares.costPerShare());
                } else if(companySelect.getSelectedItem().equals("COST")){
                    companyChosen = "COST";
                    ownedTxt.setText(String.valueOf(costcoShares.getSharesOwned()));
                    sharePriceTxt.setText(costcoShares.costPerShare());
                } else if(companySelect.getSelectedItem().equals("NVDA")){
                    companyChosen = "NVDA";
                    ownedTxt.setText(String.valueOf(nvidiaShares.getSharesOwned()));
                    sharePriceTxt.setText(nvidiaShares.costPerShare());
                }
            }
        });

        buyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buyShares(companyChosen);
            }
        });

        sellBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sellShares(companyChosen);
            }
        });

    }

    public stockAccount currentCompany(String chosenCompany){
        stockAccount accountToReturn = null;
        if(chosenCompany.equals("GOOGL")){
            accountToReturn = googleShares;
        } else if(chosenCompany.equals("MSFT")){
            accountToReturn = microsoftShares;
        } else if(chosenCompany.equals("TSLA")){
            accountToReturn = teslaShares;
        } else if(chosenCompany.equals("COST")){
            accountToReturn = costcoShares;
        } else if(chosenCompany.equals("NVDA")){
            accountToReturn = nvidiaShares;
        }
        return accountToReturn;
    }
    public void buyShares(String corpName){
        errorTxt.setText("");
        try{
            int purchaseAmt = Integer.valueOf(buyField.getText());
            stockAccount stockCompany = currentCompany(corpName);

            String purchasingPrice = costPerShare(corpName);
            double price = Double.valueOf(purchasingPrice);

            double totalPurchaseCost = price* Double.valueOf(purchaseAmt);

            double accountBalance = bankAccount.getBalance();
            double difference = accountBalance - totalPurchaseCost;
            if(difference >= 0){
                stockCompany.addShares(purchaseAmt);
                bankAccount.withdraw(totalPurchaseCost,"Purchase of "+ purchaseAmt+" shares of "+ corpName +" for $" + totalPurchaseCost);
                ownedTxt.setText(String.valueOf(stockCompany.getSharesOwned()));
                balTxt.setText(String.valueOf(bankAccount.getBalance()));
                serializeAll();
            } else{
                errorTxt.setText("You don't have enough money to make this purchase!");
            }
        } catch(NumberFormatException e){
            errorTxt.setText("That is not an integer!");
        }
    }

    public void sellShares(String corpName){
        errorTxt.setText("");
        try{
            int sellAmt = Integer.valueOf(sellField.getText());
            stockAccount stockCompany = currentCompany(corpName);
            double amountOwned = Double.valueOf(stockCompany.getSharesOwned());
            double difference = amountOwned - Double.valueOf(stockCompany.getSharesOwned());
            String price = stockCompany.costPerShare();
            double shareCost = Double.valueOf(price);
            if(difference >= 0){
                stockCompany.removeShares(sellAmt);
                bankAccount.deposit(sellAmt*shareCost, "Sold "+sellAmt+"shares of "+corpName+" for $"+(sellAmt*shareCost));
                ownedTxt.setText(String.valueOf(stockCompany.getSharesOwned()));
                balTxt.setText(String.valueOf(bankAccount.getBalance()));
                serializeAll();
            } else{
                errorTxt.setText("You don't have that many shares to sell!");
            }
        } catch(NumberFormatException e){
            errorTxt.setText("That is not an integer!");

        }
    }

    public void serializeAll(){
        serializeShares("GOOGL",googleShares);
        serializeShares("MSFT",microsoftShares);
        serializeShares("TSLA",teslaShares);
        serializeShares("COST",costcoShares);
        serializeShares("NVDA",nvidiaShares);
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
