package accountabilibuddy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;

/**
 * Created by overt on 2/21/2017.
 */
public class StockMarketIndex {

    private StringProperty stockMarketIndexName;
    private HashMap<String, StockData> topStocks;

    /**
     * Constructor: Initializes the stock exchange name and the list of stocks
     * @param stockMarketIndexName
     */
    public StockMarketIndex(String stockMarketIndexName){
        this.stockMarketIndexName = new SimpleStringProperty(stockMarketIndexName);
        topStocks = new HashMap<>(100);
    }

    public void addToStocks(String stockName, StockData stock){
        topStocks.put(stockName, stock);
    }

    public void getStock(int index){ topStocks.get(index); }

    public String getStockMarketIndexName(){
        return this.stockMarketIndexName.get();
    }

    public StringProperty stockMarketIndexNameProperty(){
        return this.stockMarketIndexName;
    }

}
