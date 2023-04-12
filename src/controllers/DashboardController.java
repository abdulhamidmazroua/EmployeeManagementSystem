package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Employee;
import models.UserData;
import tools.EmployeeDatabase;
import tools.Helper;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_date;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_employeeID;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_firstName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_gender;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_lastName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_position;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private ComboBox<String> addEmployee_gender;

    @FXML
    private TextField addEmployee_username;

    @FXML
    private PasswordField addEmployee_password;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<String> addEmployee_position;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TableView<Employee> addEmployee_tableView;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<String, Integer> home_chart;

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

    private EmployeeDatabase db = EmployeeDatabase.getInstance();

    public void homeTotalEmployees() {
        int emp_count = db.getEmpCount();
        home_totalEmployees.setText(String.valueOf(emp_count));
    }

    public void homeTotalPresent() {
        try{
            int emp_count = db.getEmpCount();         // this should be different after we modify the database to include the presents
            home_totalPresents.setText(String.valueOf(emp_count));
        }catch(RuntimeException e){
            e.printStackTrace();
            Helper.errorAlert(e.toString());
        }
    }

    public void homeTotalInactive() {
        home_totalInactiveEm.setText("0");      // this should be different after we modify the database to include the inactive emps
    }

    public void homeChar() {
        if (home_chart.getData().size() == 2){
            home_chart.getData().clear();
        }
        XYChart.Series chart = new XYChart.Series();
        try{
            ArrayList<Object[]> weekStats = db.getWeekStats();
            for(int i=0;i<weekStats.size();i++)
                chart.getData().add(new XYChart.Data(weekStats.get(i)[0], weekStats.get(i)[1]));
            home_chart.getData().add(chart);
        }catch (RuntimeException e){
            e.printStackTrace();
            Helper.errorAlert(e.toString());
        }
    }

    private String[] listGender = {"Male", "Female", "Others"};
    public void setEmployeeGenders() {
        ObservableList listData = FXCollections.observableArrayList(listGender);
        addEmployee_gender.setItems(listData);
    }

    private String[] positionList = {"Software Engineer", "Front End Developer", "Back End Developer", "Application Developer", "Human Resource Specialist"};
    public void setEmployeePositions() {
        ObservableList listData = FXCollections.observableArrayList(positionList);
        addEmployee_position.setItems(listData);
    }

    public void setEmployeeSearch() {
        FilteredList<Employee> filter = new FilteredList<>(empListData, e -> true);
        addEmployee_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateEmployeeData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateEmployeeData.getEmployee_id().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getFirst_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getLast_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPhone().contains(searchKey)) {
                    return true;
                }
                else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getDate().contains(searchKey)) {
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Employee> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addEmployee_tableView.comparatorProperty());
        addEmployee_tableView.setItems(sortList);
    }


    private ObservableList<Employee> empListData;
    public void addEmployeeShowListData() {
        empListData = db.getEmployeeListData();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_tableView.setItems(empListData);

    }

    @FXML
    void addEmployeeAdd(ActionEvent event) {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        if (addEmployee_employeeID.getText().isEmpty()
                || addEmployee_username.getText().isEmpty()
                || addEmployee_password.getText().isEmpty()
                || addEmployee_firstName.getText().isEmpty()
                || addEmployee_lastName.getText().isEmpty()
                || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                || addEmployee_phoneNum.getText().isEmpty()
                || addEmployee_position.getSelectionModel().getSelectedItem() == null
                || image_uri == null || image_uri == "")
            Helper.errorAlert("Please fill in the blank fields");
        else {
            db.addEmployee(
                    addEmployee_employeeID.getText(),
                    addEmployee_username.getText(),
                    addEmployee_password.getText(),
                    addEmployee_firstName.getText(),
                    addEmployee_lastName.getText(),
                    addEmployee_gender.getSelectionModel().getSelectedItem(),
                    addEmployee_phoneNum.getText(),
                    addEmployee_position.getSelectionModel().getSelectedItem(),
                    image_uri,
                    sqlDate
            );
            addEmployeeShowListData();
            addEmployeeReset(event);

        }
    }

    @FXML
    void addEmployeeDelete(ActionEvent event) {

        Employee employee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        db.deleteEmployee(employee);
        addEmployeeShowListData();
    }


    @FXML
    void addEmployeeGenderList(ActionEvent event) {

    }

    private String image_uri;
    @FXML
    void addEmployeeInsertImage(MouseEvent event) {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            image_uri = file.getAbsolutePath();
            addEmployee_image.setImage(new Image(file.toURI().toString(),101, 127, false, true));
        }
    }


    @FXML
    void addEmployeePositionList(ActionEvent event) {

    }

    @FXML
    void addEmployeeReset(ActionEvent event) {
        addEmployee_employeeID.setText("");
        addEmployee_username.setText("");
        addEmployee_password.setText("");
        addEmployee_firstName.setText("");
        addEmployee_lastName.setText("");
        addEmployee_gender.getSelectionModel().clearSelection();
        addEmployee_position.getSelectionModel().clearSelection();
        addEmployee_phoneNum.setText("");
        addEmployee_image.setImage(null);
        image_uri = "";
    }

    @FXML
    void addEmployeeSelect(MouseEvent event) {
        Employee employee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        addEmployee_employeeID.setText(employee.getEmployee_id());
        addEmployee_username.setText(employee.getUsername());
        addEmployee_password.setText(employee.getPassword());
        addEmployee_firstName.setText(employee.getFirst_name());
        addEmployee_lastName.setText(employee.getLast_name());
        addEmployee_gender.setValue(employee.getGender());
        addEmployee_position.setValue(employee.getPosition());
        addEmployee_phoneNum.setText(employee.getPhone());
        try{
            addEmployee_image.setImage(new Image(employee.getImage(),101, 127, false, true));
            image_uri = employee.getImage();
        }catch (NullPointerException e){
            System.out.println("There is no such image");
        }
    }

    @FXML
    void addEmployeeUpdate(ActionEvent event) {
        Employee employee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        ArrayList<String[]> updateList = new ArrayList<>();
        if (employee.getUsername() != addEmployee_username.getText())
            updateList.add(new String[] {"username", addEmployee_username.getText()});
        String str = addEmployee_password.getText();
        String emp = employee.getPassword();
        if (!str.equals(emp))
            updateList.add(new String[] {"password", addEmployee_password.getText()});
        if (!employee.getFirst_name().equals(addEmployee_firstName.getText()))
            updateList.add(new String[] {"first_name", addEmployee_firstName.getText()});
        if (!employee.getLast_name().equals(addEmployee_lastName.getText()))
            updateList.add(new String[] {"last_name", addEmployee_lastName.getText()});
        if (!employee.getGender().equals(addEmployee_gender.getSelectionModel().getSelectedItem()))
            updateList.add(new String[] {"gender", addEmployee_gender.getSelectionModel().getSelectedItem()});
        if (!employee.getPhone().equals(addEmployee_phoneNum.getText()))
            updateList.add(new String[] {"phone", addEmployee_phoneNum.getText()});
        if (!employee.getPosition().equals(addEmployee_position.getSelectionModel().getSelectedItem()))
            updateList.add(new String[] {"position", addEmployee_position.getSelectionModel().getSelectedItem()});
        try{
            if (!employee.getImage().equals(addEmployee_image.getImage().getUrl()))
                updateList.add(new String[] {"username", addEmployee_image.getImage().getUrl()});
        }catch (NullPointerException e){
            Helper.warningAlert("There will be no image for this employee");
        }

        db.updateEmployee(employee.getEmployee_id(), updateList);
        addEmployeeShowListData();
        addEmployeeReset(event);
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
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            homeTotalEmployees();
            homeTotalPresent();
            homeTotalInactive();
            homeChar();

        }else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            setEmployeeGenders();
            setEmployeePositions();
            setEmployeeSearch();

        }else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

//            salaryShowListData();
        }
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

    public void displayUsername() {
        username.setText(db.getUserData().getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        homeTotalEmployees();
        homeTotalPresent();
        homeTotalInactive();
        homeChar();

        addEmployeeShowListData();
        setEmployeeGenders();
        setEmployeePositions();
    }
}
