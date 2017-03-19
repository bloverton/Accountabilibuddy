package accountabilibuddy.view;

import accountabilibuddy.util.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    private Button btnLogIn;

    @FXML
    private TextField txtUsernameField;

    @FXML
    private PasswordField txtPasswordField;

    @FXML
    private Label statusLabel;

    private Stage stage;

    //Test method just to swap scenes and stages
    @FXML
    private void onClickLogIn() throws IOException{
        //Check for correct username and password in database

        //Load new Stage and root layout scene first
        stage = (Stage)btnLogIn.getScene().getWindow();
        BorderPane root;
        root = FXMLLoader.load(getClass().getResource("RootLayoutView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);

        //Places Stock Exchange View to center of Root Layout
        AnchorPane stockExchange = FXMLLoader.load(getClass().getResource("StockExchangeView.fxml"));
        root.setCenter(stockExchange);

        Stock stock = YahooFinance.get("^IXIC");

        stock.print();
    }

    @FXML
    private void onClickSignUp() throws IOException{
        String username = txtUsernameField.getText();
        String password = txtPasswordField.getText();

        DatabaseConnection dbConnection = new DatabaseConnection("C:/");
        dbConnection.connect();
        if(dbConnection.isValidUsername("", "", username)){
            dbConnection.insertUser("", username, password);
        }else{
            statusLabel.setText("That username has been taken already");
        }
    }

}
