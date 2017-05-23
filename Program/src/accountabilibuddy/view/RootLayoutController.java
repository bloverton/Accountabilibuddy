package accountabilibuddy.view;

import accountabilibuddy.model.User;
import accountabilibuddy.util.DatabaseConnection;
import accountabilibuddy.util.Serialization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import  accountabilibuddy.model.StockData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RootLayoutController extends ListCell<StockData> {

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnOptionsHelp;

    @FXML
    private Button btnSaved = new Button();

    @FXML
    private Button btnUsername = new Button();

    @FXML
    private TextField txtFieldSearch;

    private Stage stage;

    private StockData stockData;

    private ArrayList<String> saved;

    private User currentUser;

    public void initData(User currentUser) throws IOException, SQLException{
        btnUsername.setText(currentUser.getUsername());
        this.currentUser = currentUser;
        saved = DatabaseConnection.getFavorites(currentUser.getUsername(), currentUser.getPassword());
    }
    /**
     * Note: NOT COMPLETED
     * Displays stocks that match the name in search box
     * @throws IOException
     */
    @FXML
    private void onActionSearch() throws IOException {
        String searchStock = txtFieldSearch.getText();
        stockData = new StockData(searchStock);
        //Stock stock = YahooFinance.get(searchStock);
        //stock.print();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StockDataView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((AnchorPane)loader.load()));
        StockDataController controller = loader.<StockDataController>getController();
        controller.initData(stockData);
        stage.show();
    }



    /**Swaps scene to initial login screen when Options/Help button is clicked on
     * @throws IOException
     */
    @FXML
    private void onActionOptionsHelp() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsHelp.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((AnchorPane) loader.load()));
        stage.show();
    }

    /**Swaps scene to initial login screen when About button is clicked on
     * @throws IOException
     */
    @FXML
    private void onActionAbout() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((AnchorPane) loader.load()));
        stage.show();
    }


    /**
     * Swaps scene to initial login screen when log out button is clicked on
     * @throws IOException
     */
    @FXML
    private void onActionLogout() throws IOException, SQLException{
        //Set favorites to null in database
        String username = LoginScreenController.currentUser.getUsername();
        String password = LoginScreenController.currentUser.getPassword();
        DatabaseConnection.clearFavorites(username, password);

        //Enter all stocks back to database
        File savedStocks = new File(Serialization.FILE_PATH + "/Program/res/saved.txt");
        BufferedReader readSavedStockFile = new BufferedReader(new FileReader(savedStocks));
        String stockSymbol;
        while((stockSymbol = readSavedStockFile.readLine()) != null){
            DatabaseConnection.saveStock(username, password, stockSymbol);
        }
        readSavedStockFile.close();

        stage = (Stage)btnLogout.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("LoginScreenView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        saved = null;
    }
}
