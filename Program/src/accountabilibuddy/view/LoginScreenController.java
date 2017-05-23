package accountabilibuddy.view;

import accountabilibuddy.util.DatabaseConnection;
import accountabilibuddy.model.User;

import accountabilibuddy.util.Serialization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable{

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnLogIn;

    @FXML
    private TextField txtUsernameField;

    @FXML
    private PasswordField txtPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    private CheckBox chkboxRemember;

    private Stage stage;

    private boolean isChecked = false;

    public static User currentUser;

    public LoginScreenController(){

    }

    public void initialize(URL url, ResourceBundle rb){
        txtUsernameField.setText(getRememberedUsername());
    }

    //Test method just to swap scenes and stages
    @FXML
    private void onClickLogIn() throws IOException, SQLException{
        String username = txtUsernameField.getText();
        String password = txtPasswordField.getText();

        if(!DatabaseConnection.checkUser(username, password)){
            statusLabel.setText("Invalid username/password combination!");
            statusLabel.setTextFill(Color.web("#ff0000"));
            return;
        }

        currentUser = new User(username, password, DatabaseConnection.getFavorites(username, password));

        //Check for correct username and password in database
        statusLabel.setText("Attempting to log in");

        //Load new Stage and root layout scene first
        statusLabel.setText("Setting up personal user layout");
        setUpLayoutScene(currentUser);
    }

    @FXML
    private void onClickSignUp() throws IOException{
        stage = (Stage)btnSignUp.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("SignUpView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickRememberMe(){
        if(!chkboxRemember.isSelected()) {
            File fileLocation = new File("/Program/res/user.ser");
            String username = txtUsernameField.getText();
            if (!fileLocation.exists())
                Serialization.serialize(username, fileLocation.getPath());
            isChecked = true;
            chkboxRemember.setSelected(isChecked);
        }
        isChecked = false;
        chkboxRemember.setSelected(isChecked);
    }

    private void setUpLayoutScene(User currentUser){
        try {
            TabPane stockExchange = FXMLLoader.load(getClass().getResource("StockIndexView.fxml"));
            stage = (Stage) btnLogIn.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RootLayoutView.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            RootLayoutController controller = loader.getController();
            controller.initData(currentUser);

            stage.setScene(scene);
            stage.setResizable(true);

            //Places Stock Exchange View to center of Root Layout
            root.setCenter(stockExchange);

        }catch(IOException error){error.printStackTrace();}
        catch(SQLException error){error.printStackTrace();}
    }

    private String getRememberedUsername(){
        String serializedData = "/Program/res/user.ser";
        File fileLocation = new File(Serialization.FILE_PATH + serializedData);
        if(fileLocation.exists()) {
            System.out.println("File exists");
            String username = (String) Serialization.deserialize(serializedData);
            return username;
        }else{
            System.out.println("File does not exist");
            return "";
        }
    }
}
