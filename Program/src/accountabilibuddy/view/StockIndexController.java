package accountabilibuddy.view;

import accountabilibuddy.model.StockMarketIndex;
import accountabilibuddy.model.StockData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockIndexController implements Initializable{

    @FXML
    private Tab NASDAQ;

    @FXML
    private Tab DJI;

    @FXML
    private Tab SP50;

    @FXML
    private ListView<StockData> NASDAQStockList;

    @FXML
    private ListView<StockData> DJIStockList;

    @FXML
    private ListView<StockData> SP50StockList;

    private ObservableList<StockData> NASDAQStockData = FXCollections.observableArrayList();
    private ObservableList<StockData> SP50StockData = FXCollections.observableArrayList();
    private ObservableList<StockData> DJIStockData = FXCollections.observableArrayList();

    public StockIndexController() throws IOException {
        StockMarketIndex NASDAQ = new StockMarketIndex("NASDAQ", "/Program/res/NASDAQ.txt", 1);
        StockMarketIndex SP50 = (new StockMarketIndex("SP50", "/Program/res/SP50.txt", 1));
        StockMarketIndex DJI = (new StockMarketIndex("DJI", "/Program/res/DJI.txt", 1));

        transferContents(NASDAQ, NASDAQStockData);
        transferContents(SP50, SP50StockData);
        transferContents(DJI, DJIStockData);
    }

    public void initialize(URL location, ResourceBundle resources){
        NASDAQStockList.setItems(NASDAQStockData);
        SP50StockList.setItems(SP50StockData);
        DJIStockList.setItems(DJIStockData);

        NASDAQStockList.setCellFactory(NASDAQStockListView -> new StockViewCell());
        SP50StockList.setCellFactory(SP50StockDataView -> new StockViewCell());
        DJIStockList.setCellFactory(DJIStockDataView -> new StockViewCell());
    }

    public void transferContents(StockMarketIndex source, ObservableList<StockData> target){
        int i = 0;
        String[] keys = source.getStockKeys();
        while(source.getStock(keys[i++]) != null){
            target.addAll(source.getStock(keys[i]));
        }
    }

    public ObservableList<StockMarketIndex> getStockMarketIndex() { return getStockMarketIndex(); }
}
