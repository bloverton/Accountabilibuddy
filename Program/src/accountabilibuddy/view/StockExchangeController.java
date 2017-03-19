package accountabilibuddy.view;

import accountabilibuddy.model.StockMarketIndex;
import accountabilibuddy.model.StockData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class StockExchangeController {

    private ObservableList<StockMarketIndex> stockMarketIndex = FXCollections.observableArrayList();
    private ObservableList<StockData> stockData = FXCollections.observableArrayList();

    public StockExchangeController(){
        this.stockMarketIndex.add(new StockMarketIndex("NASDAQ"));
        this.stockMarketIndex.add(new StockMarketIndex("NYSE"));
        this.stockMarketIndex.add(new StockMarketIndex("LSE"));

    }

    public ObservableList<StockMarketIndex> getStockMarketIndex(){
        return getStockMarketIndex();
    }

}
