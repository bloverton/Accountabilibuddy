/**
 * Created by overt on 2/15/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreenView.fxml"));
        primaryStage.setTitle("Accountabilibuddy");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
