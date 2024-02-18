package src.src;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class stockAccount implements Serializable{
    String companyName;
    int sharesOwned;

    public String getCompanyName(){
        return companyName;
    }

    public int getSharesOwned(){
        return sharesOwned;
    }

    public String costPerShare(){
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

    public void addShares(int amountOfShares){
        sharesOwned += amountOfShares;
    }

    public void removeShares(int amountOfShares){
        sharesOwned -= amountOfShares;
    }

    public stockAccount(String corpName, int startingShares){
        companyName = corpName;
        sharesOwned = startingShares;
    }

    public stockAccount(String corpName){
        companyName = corpName;
        sharesOwned = 0;
    }


    public String toString(){
        return ("Company: " + companyName + "; Shares Owned: " + String.valueOf(sharesOwned));
    }
}
