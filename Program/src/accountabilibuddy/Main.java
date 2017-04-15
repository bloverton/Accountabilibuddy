package accountabilibuddy;

import accountabilibuddy.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class Main extends Application{

    private Scene currentScene;
    private Stage currentStage;

    private Parent root;

    public Main(){

    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.currentStage = primaryStage;
        this.currentStage.setTitle("Accountabilibuddy");
        initLoginScreen();
    }

    /**
     * Initializes login scene when program first runs
     */
    private void initLoginScreen(){
        swapRoot("view/LoginScreenView.fxml");
    }

    /**
     * Swaps root to another fxml file and swaps primary scene
     * @param fxmlFilePath - Location and name of FXML file
     */
    public void swapRoot(String fxmlFilePath) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            currentScene = new Scene(root);
            currentStage.setScene(currentScene);
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
