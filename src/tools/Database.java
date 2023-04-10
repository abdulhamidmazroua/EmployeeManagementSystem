package tools;

import java.sql.*;

public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public Database() {
        this("jdbc:mysql://localhost:3306/company", "scott", "tiger");
    }
    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        setConnection();
    }

    private void setConnection() {
        try {
            // loading driver
            Class.forName("com.mysql.jdbc.Driver");
            // connecting
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }
}

