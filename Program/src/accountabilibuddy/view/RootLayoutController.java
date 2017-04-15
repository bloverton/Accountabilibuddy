package accountabilibuddy.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class RootLayoutController {

    @FXML
    private Button btnLogout;

    @FXML
    private TextField txtFieldSearch;

    private Stage stage;

    /**
     * Note: NOT COMPLETED
     * Displays stocks that match the name in search box
     * @throws IOException
     */
    @FXML
    private void onActionSearch() throws IOException {
        String searchStock = txtFieldSearch.getText();
        Stock stock = YahooFinance.get(searchStock);
        stock.print();
    }

    /**
     * Swaps scene to initial login screen when log out button is clicked on
     * @throws IOException
     */
    @FXML
    private void onActionLogout() throws IOException{
        stage = (Stage)btnLogout.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("LoginScreenView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
    }

}
