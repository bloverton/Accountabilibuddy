package accountabilibuddy.view;

import accountabilibuddy.model.StockData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by overt on 4/15/2017.
 */
public class StockDataController {

    @FXML
    private Label lblStockName;

    @FXML
    private Label lblStockIndex;

    @FXML
    private Label lblStockSymbol;

    @FXML
    private Label lblStockPrice;

    @FXML
    private Label lblCurrency;

    @FXML
    private Label lblPercentageChange;

    @FXML
    private Label lblInvestBoolean;

    @FXML
    private Label lblInvestDescription;

    @FXML
    private Label lblAnnualYield;

    @FXML
    private Label lblPERatio;

    private Stage stage;

    private DecimalFormat df2 = new DecimalFormat("#0.00");

    public void initData(StockData stockData) throws IOException{
        double stockChange = stockData.getStockChange();
        double stockPercentChange = stockChange / stockData.getStockPrice() * 100;

        lblStockName.setText(stockData.getStockName());
        lblStockSymbol.setText(stockData.getStockSymbol());
        lblStockPrice.setText(String.valueOf(df2.format(stockData.getStockPrice())));
        lblCurrency.setText(StockData.Currency);
        lblStockIndex.setText(stockData.getStockIndex() + ": ");
        lblInvestDescription.setText(stockData.getInvestmentMSG());
        lblInvestDescription.setWrapText(true);
        lblAnnualYield.setText(String.valueOf(stockData.getAnnualYield()));
        lblPERatio.setText(String.valueOf(stockData.getPriceEarningsRatio()));
        if(stockData.getStockIndex() == null) {
            String name = stockData.getStockSymbol();
            //Stock stock = YahooFinance.get("AAPL");
            Stock stock = YahooFinance.get(name);
            String stocks = stock.getStockExchange();
            //stock.print();
            lblStockIndex.setText(stocks + ": ");
        }
        else {
            lblStockIndex.setText(stockData.getStockIndex() + ": ");
        }


        if(stockData.getIsGoodInvestment()) {
            lblInvestBoolean.setText("INVEST");
            lblInvestBoolean.setTextFill(Color.web("#008000"));
        }
        else {
            lblInvestBoolean.setText("DO NOT INVEST");
            lblInvestBoolean.setTextFill(Color.web("#ff0000"));
        }


        if(stockChange > 0) {
            lblPercentageChange.setText("\t" + String.valueOf(df2.format(stockChange) + " (" + df2.format(stockPercentChange) + "%)"));
            lblPercentageChange.setTextFill(Color.web("#008000"));
        }else{
            stockChange *= -1;
            stockPercentChange *= -1;
            lblPercentageChange.setText("\t" + String.valueOf(df2.format(stockChange) + " (" + df2.format(stockPercentChange) + "%)"));
            lblPercentageChange.setTextFill(Color.web("#ff0000"));
        }
    }
}
