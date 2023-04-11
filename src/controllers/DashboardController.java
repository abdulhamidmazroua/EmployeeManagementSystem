package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.Helper;

import java.util.Optional;

public class DashboardController {

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private TableColumn<?, ?> addEmployee_col_date;

    @FXML
    private TableColumn<?, ?> addEmployee_col_employeeID;

    @FXML
    private TableColumn<?, ?> addEmployee_col_firstName;

    @FXML
    private TableColumn<?, ?> addEmployee_col_gender;

    @FXML
    private TableColumn<?, ?> addEmployee_col_lastName;

    @FXML
    private TableColumn<?, ?> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<?, ?> addEmployee_col_position;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private ComboBox<?> addEmployee_gender;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<?> addEmployee_position;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TableView<?> addEmployee_tableView;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button salary_btn;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableColumn<?, ?> salary_col_employeeID;

    @FXML
    private TableColumn<?, ?> salary_col_firstName;

    @FXML
    private TableColumn<?, ?> salary_col_lastName;

    @FXML
    private TableColumn<?, ?> salary_col_position;

    @FXML
    private TableColumn<?, ?> salary_col_salary;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private Label salary_firstName;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_lastName;

    @FXML
    private Label salary_position;

    @FXML
    private TextField salary_salary;

    @FXML
    private TableView<?> salary_tableView;

    @FXML
    private Button salary_updateBtn;

    @FXML
    private Label username;

    @FXML
    void addEmployeeAdd(ActionEvent event) {

    }

    @FXML
    void addEmployeeDelete(ActionEvent event) {

    }

    @FXML
    void addEmployeeGendernList(ActionEvent event) {

    }

    @FXML
    void addEmployeeInsertImage(MouseEvent event) {

    }

    @FXML
    void addEmployeePositionList(ActionEvent event) {

    }

    @FXML
    void addEmployeeReset(ActionEvent event) {

    }

    @FXML
    void addEmployeeSearch(KeyEvent event) {

    }

    @FXML
    void addEmployeeSelect(MouseEvent event) {

    }

    @FXML
    void addEmployeeUpdate(ActionEvent event) {

    }


    @FXML
    void salaryReset(ActionEvent event) {

    }

    @FXML
    void salarySelect(MouseEvent event) {

    }

    @FXML
    void salaryUpdate(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }

    private double x = 0;
    private double y = 0;
    @FXML
    void logout(ActionEvent event) {
        Optional<ButtonType> option = Helper.confirmAlert("Are you sure you want to logout");
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent e) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage primaryStage = (Stage) main_form.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

}
