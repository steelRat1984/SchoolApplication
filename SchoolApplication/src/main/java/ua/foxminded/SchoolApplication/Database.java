package ua.foxminded.SchoolApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static final String CONNECTION = "jdbc:postgresql://localhost:5432/school_app";
	private static final String USER = "school_admin";
	private static final String PASSWORD = "1234";

	public static java.sql.Connection connection() throws SQLException {
		return DriverManager.getConnection(CONNECTION, USER, PASSWORD);

	}

}
