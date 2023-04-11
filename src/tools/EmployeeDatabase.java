package tools;


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
                return true;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.errorAlert(e.toString());
        }
        return false;
    }

    public int getEmpCount() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select count(employee_id) from employee");
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Object[]> getWeekStats() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select date, count(employee_id) from employee group by timestamp(date) asc limit 7");
            // result must be exactly 7 records
            ArrayList<Object[]> weekStats = new ArrayList<>();
            while (resultSet.next()){
                weekStats.add(new Object[] {resultSet.getString(1), resultSet.getInt(2)});
            }
            if (weekStats.size()<7){
                throw new RecordsNotEnoughException("There must be at least 7 records in the database");
            }else{
                return weekStats;
            }


        } catch (SQLException | RecordsNotEnoughException e) {
            throw new RuntimeException(e);
        }
    }

}

