package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.EmployeeDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tools.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private EmployeeDatabase db = EmployeeDatabase.getInstance();

    private double x=0;
    private double y=0;
    @FXML
    void onLogIn(MouseEvent event) throws IOException {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            System.out.println("Please fill in the blank fields");
            Helper.errorAlert("Please fill in the blank fields");
        }else{
            boolean authorized = db.authorize(username.getText(), password.getText());
            if (authorized) {
                System.out.println("welcome to the dashboard");
                Helper.informationAlert("Successful Login");
                // show the dashboard
                loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../views/dashboard.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) ->{
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) ->{
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

            }else {
                System.out.println("Incorrect username or password!");
                Helper.errorAlert("Incorrect username or password!");
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
