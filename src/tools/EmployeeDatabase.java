package tools;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Employee;
import models.UserData;

import java.sql.*;
import java.util.ArrayList;

// this class is created with the singleton design pattern to ensure
// only one instance is created (to prevent multiple connections to the database)
public class EmployeeDatabase {
    private static EmployeeDatabase empDB;
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private UserData userData;

    private EmployeeDatabase() {
        this("jdbc:mysql://localhost:3306/company", "scott", "tiger");
    }
    private EmployeeDatabase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        setConnection();
    }

    public static EmployeeDatabase getInstance() {
        if (empDB == null)
            empDB = new EmployeeDatabase();
        return empDB;
    }

    private void setConnection() {
        try {
            // loading driver
            Class.forName("com.mysql.jdbc.Driver");
            // connecting
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Helper.errorAlert(e.toString());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("closing database connection");
        connection.close();
        super.finalize();
    }

    public boolean authorize(String username, String password) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from employee where " +
                "username=? and password=?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userData = new UserData();
                userData.setUsername(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                userData.setImage_uri(resultSet.getString("image"));
                return true;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.errorAlert(e.toString());
        }
        return false;
    }

    public UserData getUserData() {
        return userData;
    }

    public int getEmpCount() {
        int count;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select count(employee_id) from employee");
            resultSet.next();
            count = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public ArrayList<Object[]> getWeekStats() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select date, count(employee_id) from employee group by timestamp(date) asc limit 7");
            // result must be exactly 7 records
            ArrayList<Object[]> weekStats = new ArrayList<>();
            while (resultSet.next()){
                weekStats.add(new Object[] {resultSet.getString(1), resultSet.getInt(2)});
            }
            resultSet.close();
            if (weekStats.size()<7){
                throw new RecordsNotEnoughException("There must be at least 7 records in the database");
            }else{
                return weekStats;
            }


        } catch (SQLException | RecordsNotEnoughException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Employee> getEmployeeListData() {
        ObservableList<Employee> empListData = FXCollections.observableArrayList();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from employee");
            while(resultSet.next()) {
                empListData.add(
                        new Employee(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        // because the nine is for the image
                        resultSet.getString(10))
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            Helper.errorAlert(e.toString());
        }
        return empListData;
    }

    public void addEmployee(String employee_id, String username, String password, String first_name, String last_name, String gender,
                            String phone, String position, String image_uri, Date date) {
        try( PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into employee values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )){
            preparedStatement.setString(1, employee_id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, first_name);
            preparedStatement.setString(5, last_name);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, position);
            preparedStatement.setString(9, image_uri);
            preparedStatement.setString(10, date.toString());

            int rowsAffected = preparedStatement.executeUpdate();
            Helper.informationAlert(rowsAffected + " rows affected");

        } catch (SQLException e) {
            Helper.errorAlert(e.toString());
        }
    }

    public void deleteEmployee(Employee employee) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from employee where employee_id=?")) {
            preparedStatement.setString(1, employee.getEmployee_id());
            int rowsAffected = preparedStatement.executeUpdate();
            Helper.informationAlert(rowsAffected + " rows affected");
        } catch (SQLException e) {
            Helper.errorAlert(e.toString());
        }
    }

    public void updateEmployee(String employee_id, ArrayList<String[]> updateList) {
        int numberofcolumns = updateList.size();

        StringBuilder sqlStatement = new StringBuilder("update employee set ");
        for(int i=0; i < numberofcolumns; i++){
            if (i==0){
                sqlStatement.append( updateList.get(i)[0] + "=?");
                continue;
            }
            sqlStatement.append(", " + updateList.get(i)[0] + "=?");
        }
        sqlStatement.append(" where employee_id=?");

        try(PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sqlStatement))) {
            int i;
            for(i=1;i<=numberofcolumns;i++){
                preparedStatement.setString(i, updateList.get(i-1)[1]);
            }
            preparedStatement.setString(i, employee_id);
            int rowsAffected = preparedStatement.executeUpdate();
            Helper.informationAlert(rowsAffected + " rows affected");
        } catch (SQLException e) {
            Helper.errorAlert(e.toString());
        }
    }
}

