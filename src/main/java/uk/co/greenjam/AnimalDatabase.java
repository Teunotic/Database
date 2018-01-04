package uk.co.greenjam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AnimalDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TEST";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";
    private static final String TABLE_NAME = "animals";

    private Connection connection = null;

    public AnimalDatabase(){
        makeJDBCConnection();
    }


    private void makeJDBCConnection(){
        driverRegistered();
        connectDB();
    }

    private void driverRegistered()  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        log("JDBC Driver Registered!");
    }

    private void connectDB() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if (connection != null) {
                log("Connection Successful!");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addDataToDB(String name, String type) throws SQLException{
        log("adding record");
        PreparedStatement preparedStatement = null;

        String insertQueryStatement = "INSERT  INTO  " + TABLE_NAME + " (name,type) values (?,?)";

        preparedStatement = connection.prepareStatement(insertQueryStatement);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, type);
        // execute insert SQL statement
        preparedStatement.execute();
        log( name + " added successfully");

    }

    public ResultSet getDataFromDB() throws SQLException {
        PreparedStatement preparedStatement = null;

        String getQueryStatement = "SELECT * FROM " + TABLE_NAME;

        preparedStatement = connection.prepareStatement(getQueryStatement);

        // Execute the Query, and get a java ResultSet
        ResultSet rs = preparedStatement.executeQuery();
        return rs;


    }

    private static void log(String string) {
        System.out.println(string);

    }
}