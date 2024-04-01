package ua.foxminded.SchoolApplication.DAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import ua.foxminded.SchoolApplication.DAO.Database;

class DatabaseTest {

	@Test
	void testConnectionSuccess() {	
		try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
			Connection mockConnection = mock(Connection.class);
			mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
					.thenReturn(mockConnection);

			Connection connection = Database.connection();

			assertNotNull(connection);
			assertEquals(mockConnection, connection);
		}
	}

	@Test
	void testConnectionFailure() {
		SQLException sqlException = new SQLException("Connection failure"); 
		try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
			mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
					.thenThrow(sqlException);

			Exception exception = assertThrows(RuntimeException.class, Database::connection);
			assertEquals("Cannot connect to the database", exception.getMessage());
		}
	}

}
