package uk.co.greenjam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AnimalDatabase {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;

    public static void main(String[] argv) {

        try {
            makeJDBCConnection();

            addDataToDB("Tiger", "Cat");
            addDataToDB("Fido", "Dog");

            getDataFromDB();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("JDBC driver. Not found");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEST", "root", "password");
            if (connection != null) {
                log("Connection Successful!");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    private static void addDataToDB(String name, String type) {
        log("adding record");
        try {
            String insertQueryStatement = "INSERT  INTO  animals  (name,type) values (?,?)";

            preparedStatement = connection.prepareStatement(insertQueryStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            // execute insert SQL statement
            preparedStatement.execute();
            log( name + " added successfully");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromDB() {

        try {
            String getQueryStatement = "SELECT * FROM animals";

            preparedStatement = connection.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = preparedStatement.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");

                System.out.printf("%s, %s\n", name, type);
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }

    private static void log(String string) {
        System.out.println(string);

    }
}