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
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    protected void setConnection(Connection connection){
        this.connection = connection;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement){
        this.preparedStatement = preparedStatement;
    }

    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }

    public AnimalDatabase(){
        // Use defaults if no connection information provided
        makeJDBCConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    }

    public AnimalDatabase(String url, String username, String password){
        makeJDBCConnection(url,username,password);
    }

    private void makeJDBCConnection(String url, String username, String password){
        driverRegistered();
        connectDB(url,username,password);
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

    private void connectDB(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
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

        String insertQueryStatement = "INSERT  INTO  " + TABLE_NAME + " (name,type) values (?,?)";

        preparedStatement = connection.prepareStatement(insertQueryStatement);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, type);
        // execute insert SQL statement
        preparedStatement.execute();
        log( name + " added successfully");

    }

    public ResultSet getDataFromDB() throws SQLException {
        String getQueryStatement = "SELECT * FROM " + TABLE_NAME;

        preparedStatement = connection.prepareStatement(getQueryStatement);

        // Execute the Query, and get a java ResultSet
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public int numberOfAnimals() throws SQLException {
        String getQueryStatement = "SELECT COUNT(*) FROM " + TABLE_NAME;

        preparedStatement = connection.prepareStatement(getQueryStatement);

        // Execute the Query, and get a java ResultSet
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean hasRecords() throws SQLException{
        resultSet = getDataFromDB();
        return resultSet.next();
    }

    private static void log(String string) {
        System.out.println(string);
    }
}