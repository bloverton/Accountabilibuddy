package accountabilibuddy.view;

import accountabilibuddy.model.StockData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

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
    private Label lblInvestStatus;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(StockData stockData, boolean empty){
        super.updateItem(stockData, empty);

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
            lblStockPrice.setText("$" + String.valueOf(stockData.getStockPrice()));
            lblInvestStatus.setText("Yes");

            setText(null);
            setGraphic(gridPane);
        }
    }
}
