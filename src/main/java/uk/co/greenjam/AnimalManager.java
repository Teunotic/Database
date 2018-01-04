package uk.co.greenjam;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TEST";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args){
        AnimalDatabase animalDatabase = new AnimalDatabase();
        try {
            if (!animalDatabase.hasRecords()) {
                animalDatabase.addDataToDB("felix", "cat");
                animalDatabase.addDataToDB("fido", "dog");
            }
            ResultSet rs = animalDatabase.getDataFromDB();
            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                System.out.printf("%s, %s\n", name, type);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

