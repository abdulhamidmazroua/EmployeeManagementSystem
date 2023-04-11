package controllers;

import tools.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tools.Helper;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    // Database
    private Database db = new Database();   // creates a database instance and initialize it with default url, username, pass

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void onLogIn(MouseEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            System.out.println("Please fill in the blank fields");
            Helper.ErrorBox("Please fill in the blank fields");
        }else{
            boolean authorized = db.authorize(username.getText(), password.getText());
            if (authorized) {
                System.out.println("welcome to the dashboard");
                Helper.InformationBox("Successful Login");
                // show the dashboard
                loginBtn.getScene().getWindow().hide();


            }else {
                System.out.println("Incorrect username or password!");
                Helper.ErrorBox("Incorrect username or password!");
            }
        }

    }
    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
