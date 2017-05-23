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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by overt on 5/13/2017.
 */
public class SignUpController {

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnBack;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRepeatPassword;

    private Stage stage;

    @FXML
    private void onClickSignUp() throws SQLException, IOException{
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean isValidPasswordCheck = txtPassword.getText().equals(txtRepeatPassword.getText());
        if (!isValidPasswordCheck) {
            statusLabel.setText("Passwords do not match! Please re-enter!");
            statusLabel.setTextFill(Color.web("#ff0000"));
            return;
        }
        DatabaseConnection.signUp(username, password);
        onClickBack();
    }

    @FXML
    private void onClickBack() throws IOException {
        stage = (Stage)btnBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("LoginScreenView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
