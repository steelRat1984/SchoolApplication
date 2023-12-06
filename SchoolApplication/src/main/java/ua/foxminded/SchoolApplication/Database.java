package ua.foxminded.SchoolApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	public static final String CONNECTION = "jdbc:postgresql://localhost:5432/school_app";
	public static final String USER = "school_admin";
	public static final String PASSWORD = "1234";

	public static java.sql.Connection connection() {
		try {
			return DriverManager.getConnection(CONNECTION, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
