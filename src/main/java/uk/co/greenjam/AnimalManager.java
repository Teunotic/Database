package uk.co.greenjam;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalManager {

    public static void main(String[] args){
        AnimalDatabase animalDatabase = new AnimalDatabase();
        try {
            if (!animalDatabase.getDataFromDB().next()) {
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

