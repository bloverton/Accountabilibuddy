package accountabilibuddy.view;

import accountabilibuddy.util.DatabaseConnection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

    @FXML
    private ProgressIndicator progressIndicator;

    private Stage stage;

    public LoginScreenController(){
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
    }

    //Test method just to swap scenes and stages
    @FXML
    private void onClickLogIn() throws IOException{
        progressIndicator.setVisible(true);
        //Check for correct username and password in database
        statusLabel.setText("Attempting to log in");

        //Load new Stage and root layout scene first
        statusLabel.setText("Setting up personal user layout");
        Platform.runLater(() -> setUpLayoutScene());
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

    private void setUpLayoutScene(){
        try {
            TabPane stockExchange = FXMLLoader.load(getClass().getResource("StockIndexView.fxml"));
            stage = (Stage) btnLogIn.getScene().getWindow();
            BorderPane root;
            root = FXMLLoader.load(getClass().getResource("RootLayoutView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);

            //Places Stock Exchange View to center of Root Layout
            root.setCenter(stockExchange);
        }catch(IOException error){error.printStackTrace();}
    }

}
