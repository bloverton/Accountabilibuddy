package accountabilibuddy.view;

import accountabilibuddy.model.StockMarketIndex;
import accountabilibuddy.model.StockData;
import accountabilibuddy.util.Serialization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StockIndexController implements Initializable{

    @FXML
    private ListView<StockData> NASDAQStockList;

    @FXML
    private ListView<StockData> DJIStockList;

    @FXML
    private ListView<StockData> SP50StockList;

    @FXML
    private ListView<StockData> userFavoriteStockList;

    private ObservableList<StockData> NASDAQStockData = FXCollections.observableArrayList();
    private ObservableList<StockData> SP50StockData = FXCollections.observableArrayList();
    private ObservableList<StockData> DJIStockData = FXCollections.observableArrayList();

    private ObservableList<StockData> userFavoriteStockData = FXCollections.observableArrayList();

    private StockMarketIndex NASDAQ;
    private StockMarketIndex SP50;
    private StockMarketIndex DJI;

    private StockMarketIndex savedStocks;

    /**
     * Controller Constructor
     * @throws IOException
     */
    public StockIndexController() throws IOException {
        NASDAQ = new StockMarketIndex("NASDAQ", "/Program/res/NASDAQ.txt");
        SP50 = new StockMarketIndex("SP50", "/Program/res/SP50.txt");
        DJI = new StockMarketIndex("DJI", "/Program/res/DJI.txt");

        savedStocks = new StockMarketIndex("Fav", savedStocksLocation());

        transferContents(NASDAQ, NASDAQStockData);
        transferContents(SP50, SP50StockData);
        transferContents(DJI, DJIStockData);
        transferContents(savedStocks, userFavoriteStockData);
    }

    /**
     Called when the controller is initialized
     */
    public void initialize(URL location, ResourceBundle resources){
        NASDAQStockList.setItems(NASDAQStockData);
        SP50StockList.setItems(SP50StockData);
        DJIStockList.setItems(DJIStockData);
        userFavoriteStockList.setItems(userFavoriteStockData);

        NASDAQStockList.setCellFactory(NASDAQStockDataView -> new StockViewCell());
        SP50StockList.setCellFactory(SP50StockDataView -> new StockViewCell());
        DJIStockList.setCellFactory(DJIStockDataView -> new StockViewCell());
        userFavoriteStockList.setCellFactory(userFavoriteStockDataView -> new StockViewCell());
    }

    /**
     * Transfers data from StockMarketIndex to ObservableList for Callback
     * @param source
     * @param target
     */
    private void transferContents(StockMarketIndex source, ObservableList<StockData> target){
        int i = 0;
        String[] keys = source.getStockKeys();
        while(source.getStock(keys[i]) != null){
            target.addAll(source.getStock(keys[i++]));
        }
    }

    private String savedStocksLocation() throws IOException{
        Object[] savedFavorites = LoginScreenController.currentUser.getFavorites().toArray();
        String[] savedFavoritesString = Arrays.copyOf(savedFavorites, savedFavorites.length, String[].class);
        String savedTxt = "/Program/res/saved.txt";
        java.io.File saveLocation = new java.io.File(Serialization.FILE_PATH + savedTxt);
        FileWriter writer = new FileWriter(saveLocation);
        for(String str: savedFavoritesString) {
            writer.write(str);
            writer.write(System.getProperty( "line.separator" ));
        }
        writer.close();
        return savedTxt;
    }

    public void refreshStocks() throws IOException{
        savedStocks = new StockMarketIndex("Fav", savedStocksLocation());
        transferContents(savedStocks, userFavoriteStockData);
        userFavoriteStockList.setItems(userFavoriteStockData);
        userFavoriteStockList.setCellFactory(userFavoriteStockDataView -> new StockViewCell());
    }
}