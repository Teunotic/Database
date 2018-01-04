package uk.co.greenjam;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestAnimalDatabase  {

    @Mock
    AnimalDatabase animalDatabaseMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testQuery()  {

    }
}