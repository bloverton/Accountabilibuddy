package accountabilibuddy.model;

import accountabilibuddy.util.CalculationUtilities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 2/22/2017.
 */
public class StockData extends StockDisplay{

    //Make this change when implementing options menu
    public static String Currency = "USD";

    private String stockIndex;
    private Stock stock;
    private String stockSymbol;

    private int predictabilityWeight;
    private int betaValue;
    private int priceEarningsRatio;
    private int annualYield;

    private boolean stockVolatility;

    /**
     * Creates a new stock that retrieves info from Yahoo Finance API
     * @param stockName: The name of the stock (Case Sensitive)
     * @throws IOException: Handles Yahoo Finance exception
     */
    public StockData(String stockName) throws IOException{
        super(stockName);
        this.stock = YahooFinance.get(stockName);
        this.stockSymbol = stockName;
        updateStockInfo();
    }

    public void setStockIndex(String stockIndex){
        this.stockIndex = stockIndex;
    }

    /**
     * Sets the beta value of the stock. Determines the predictability weight of a stock.
     * Beta = Covariance(Stock's % Daily Change, Index's % Daily Change) / Variance (Index's % Daily Change)
     * Gets the closing prices of stocks from the past month
     * Note: Not completed
     */
    /*
    public void setBetaValue(){
        //Gets stock data from 1 month ago
        Calendar from  = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.DATE, -1);

        //Holds data for stocks and the price of stock and stock index
        List<HistoricalQuote> stockHistory = null;
        int[] stockHistoryPrice = new int[30];
        int[] stockIndexHistoryPrices = new int[30];

        //Puts stock info in list
        try {
            stockHistory = this.stock.getHistory(from, to, Interval.DAILY);
        }catch(IOException IOError){
            IOError.printStackTrace();
        }

        //Puts price info into array. Both for stock and stock index
        for(int i = 0; i < 30; i++) {
            stockHistoryPrice[i] = stockHistory.get(i).getClose().intValue();
            stockIndexHistoryPrices[i] = stockHistory.get(i).getClose().intValue();
        }

        //Temporary Value for beta value till method is complete
        this.betaValue = 1;
    }
    */


    private void setPriceEarningsRatio(){
        if(stock.getStats().getPe() == null)
            this.priceEarningsRatio = 0;
        else
            this.priceEarningsRatio = stock.getStats().getPe().intValue();
    }

    private void setAnnualYield(){
        if(stock.getDividend().getAnnualYield() == null)
            this.annualYield = 0;
        else
            this.annualYield = stock.getDividend().getAnnualYield().intValue();
    }

    /**
     * Sets stock's volatility based on its beta value
     */
    private void setStockVolatility(){
        this.stockVolatility = this.betaValue > 1;
    }

    private void setPredictabilityWeight(){
        //Calculation for stock's predictability weight
        final double MAX_BETA_WEIGHT = Math.E;
        final double MAX_PE_WEIGHT = MAX_BETA_WEIGHT / 2;
        final double MAX_ANNUAL_YIELD_WEIGHT = MAX_PE_WEIGHT / 2;

        //Predictability weight of a stock formula
        this.predictabilityWeight += ((Math.pow(Math.E, this.betaValue) * 0.4) + (Math.pow(this.priceEarningsRatio, MAX_PE_WEIGHT) * 0.30) + (Math.pow(this.annualYield, MAX_ANNUAL_YIELD_WEIGHT) * 0.3));

    }

    /**
     * Updates all info of a stock
     */
    public void updateStockInfo(){
        //setBetaValue();
        setStockVolatility();
        setPriceEarningsRatio();
        setAnnualYield();
        setPredictabilityWeight();
    }


    /**
     * @return Boolean value for stock's volatility
     */
    public boolean isVolatile(){
        return this.stockVolatility;
    }

    /**
     * @return (insert min and max predictability weights)
     */
    public int getPredictabilityWeight(){
        return this.predictabilityWeight;
    }

    /**
     * @return The beta value of the stock
     */
    public int getBetaValue(){
        return this.betaValue;
    }

    /**
     * @return The annual yield of the stock
     */
    public int getAnnualYield(){
        return this.annualYield;
    }

    /**
     * @return The PE ratio of the stock
     */
    public int getPriceEarningsRatio() { return this.priceEarningsRatio; }

    /**
     * @return The instance of the stock
     */
    public Stock getStock(){
        return this.stock;
    }

    public String getStockIndex(){return this.stockIndex;}

    public String getStockSymbol(){ return this.stockSymbol; }

    public String getStockName(){ return this.stock.getName(); }

    public double getStockPrice(){ return this.stock.getQuote().getPrice().doubleValue(); }

    public double getStockChange(){ return stock.getQuote().getPrice().doubleValue() - stock.getQuote().getPreviousClose().doubleValue(); }
}
