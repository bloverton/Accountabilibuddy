package accountabilibuddy.view;

import accountabilibuddy.model.StockData;
import accountabilibuddy.util.Serialization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by overt on 4/15/2017.
 */
public class StockViewCell extends ListCell<StockData> {

    @FXML
    private Label lblStockName;

    @FXML
    private Label lblStockSymbol;

    @FXML
    private Label lblStockPrice;

    @FXML
    private Label lblStockCurrency;

    @FXML
    private Label lblStockChange;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private CheckBox chkboxSave;

    private StockData stockData;

    private FXMLLoader mLLoader;

    private Stage stage;

    private DecimalFormat df2 = new DecimalFormat("#0.00");

    @Override
    protected void updateItem(StockData stockData, boolean empty){
        super.updateItem(stockData, empty);
        this.stockData = stockData;

        if(empty || stockData == null){
            setText(null);
            setGraphic(null);
        }else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("ViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
            lblStockName.setText(stockData.getStockName());
            lblStockSymbol.setText(stockData.getStockSymbol());
            lblStockPrice.setText(String.valueOf(df2.format(stockData.getStockPrice())));
            lblStockCurrency.setText(StockData.Currency);

            double stockChange = stockData.getStockChange();
            double stockPercentChange = stockChange / stockData.getStockPrice() * 100;
            if(stockChange > 0) {
                lblStockChange.setText(String.valueOf(df2.format(stockChange)) + " (" + String.valueOf(df2.format(stockPercentChange)) + "%" + ")");
                lblStockChange.setTextFill(Color.web("#008000"));
            }else{
                stockChange *= -1;
                stockPercentChange *= -1;
                lblStockChange.setText(String.valueOf(df2.format(stockChange)) + " (" + String.valueOf(df2.format(stockPercentChange))+ "%" + ")");
                lblStockChange.setTextFill(Color.web("#ff0000"));
            }
            setText(null);
            setGraphic(anchorPane);
        }
    }

    @FXML
    private void onMouseClicked() throws IOException {
        /*
        stage = (Stage)anchorPane.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("StockDataView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StockDataView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        StockDataController controller = loader.getController();
        controller.initData(stockData);
        stage.show();
    }

    @FXML
    private void onActionSave() throws IOException{
        //Finds saved.txt
        String stockSymbol = stockData.getStockSymbol();
        String savedStockLocation = Serialization.FILE_PATH + "/Program/res/saved.txt";
        BufferedReader checkStockSymbol = new BufferedReader(new FileReader(savedStockLocation));

        //Checks if the stock symbol exists in saved.txt
        //If true, return;
        String txtStockSymbol;
        while((txtStockSymbol = checkStockSymbol.readLine()) != null){
            if(txtStockSymbol.equalsIgnoreCase(stockSymbol)) {
                return;
            }
        }

        //Add to saved.txt
        FileWriter saveStock = new FileWriter(savedStockLocation, true);
        BufferedWriter addstock = new BufferedWriter(saveStock);
        addstock.write(stockSymbol);
        addstock.newLine();
        addstock.close();
    }

}
