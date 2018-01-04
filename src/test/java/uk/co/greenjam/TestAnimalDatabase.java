package uk.co.greenjam;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.SQLException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestAnimalDatabase  {
    private AnimalDatabase animalDatabase;

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        animalDatabase = new AnimalDatabase();
        animalDatabase.setConnection(connection);
        animalDatabase.setPreparedStatement(preparedStatement);
        animalDatabase.setResultSet(resultSet);
    }


    @Test
    public void testNumberOfAnimals() throws SQLException {
        // When there are animals
        int expectedNumberOfAnimals = 3;
        when(connection.prepareStatement("SELECT COUNT(*) FROM animals")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(expectedNumberOfAnimals);

        assertEquals(expectedNumberOfAnimals,animalDatabase.numberOfAnimals());
        verify(resultSet).getInt(1);

        // When there are not any animals
        when(resultSet.next()).thenReturn(false);
        assertEquals(0,animalDatabase.numberOfAnimals());
        verify(resultSet).getInt(1);
    }
}