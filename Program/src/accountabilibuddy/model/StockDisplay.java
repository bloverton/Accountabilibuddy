package accountabilibuddy.model;

import javafx.beans.property.*;

/**
 * Created by overt on 4/2/2017.
 */
public class StockDisplay {

    private StringProperty stockName;
    private DoubleProperty stockPrice;

    public StockDisplay(String stockName){
        this.stockName = new SimpleStringProperty(stockName);
    }

    public StockDisplay(String stockName, double stockPrice){
        this.stockName = new SimpleStringProperty(stockName);
        this.stockPrice = new SimpleDoubleProperty(stockPrice);
    }

    public String getStockName(){
        return this.stockName.getName();
    }
}

