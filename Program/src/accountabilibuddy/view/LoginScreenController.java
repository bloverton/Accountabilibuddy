package accountabilibuddy.view;

import accountabilibuddy.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by overt on 2/15/2017.
 */
public class LoginScreenController {

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnLogIn;

    private Stage stage;

    //Test method just to swap scenes and stages
    @FXML
    private void onClickLogIn() throws IOException{
        //Check for correct username and password in database\

        //Load new Stage and root layout scene first
        stage = (Stage)btnLogIn.getScene().getWindow();
        BorderPane root;
        root = FXMLLoader.load(getClass().getResource("RootLayoutView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //Load Stock Exchange scene to center of root scene
        AnchorPane stockExchange = FXMLLoader.load(getClass().getResource("StockExchangeView.fxml"));
        root.setCenter(stockExchange);
    }
}
