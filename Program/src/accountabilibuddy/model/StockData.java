package accountabilibuddy.model;

import accountabilibuddy.util.CalculationUtilities;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 2/22/2017.
 */
public class StockData {

    private Stock stock;

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
        this.stock = YahooFinance.get(stockName);
        updateStockInfo();
    }

    /**
     * Sets the beta value of the stock. Determines the predictability weight of a stock.
     * Beta = Covariance(Stock's % Daily Change, Index's % Daily Change) / Variance (Index's % Daily Change)
     * Gets the closing prices of stocks from the past month
     */
    public void setBetaValue(){
        Calendar from  = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        List<HistoricalQuote> stockHistory = null;
        int[] stockHistoryPrice = new int[30];
        int[] stockIndexHistoryPrices = new int[30];

        try {
            stockHistory = this.stock.getHistory(from, to, Interval.DAILY);
        }catch(IOException IOError){
            IOError.printStackTrace();
        }

        for(int i = 0; i < 30; i++) {
            stockHistoryPrice[i] = stockHistory.get(i).getClose().intValue();
            stockIndexHistoryPrices[i] = stockHistory.get(i).getClose().intValue();
        }


    }


    private void setPriceEarningsRatio(){
        this.priceEarningsRatio = stock.getStats().getPe().intValue();
    }

    private void setAnnualYield(){
        this.annualYield = stock.getDividend().getAnnualYield().intValue();
    }

    /**
     * Sets stock's volatility based on its beta value
     */
    private void setStockVolatility(){
        this.stockVolatility = this.betaValue > 1 ? true : false;
    }

    private void setPredictabilityWeight(){
        //Calculation for stock's predictability weight

    }

    /**
     * Updates all info of a stock
     */
    public void updateStockInfo(){
        setBetaValue();
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
}
