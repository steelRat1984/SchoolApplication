package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentManipulations {
	Connection connection = Database.connection();	
	
	public void createStudent (Student student) {
		String sql = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, student.getStudentID());
			statement.setInt(2, student.getGroupID());
			statement.setString(3, student.getFirstName());
			statement.setString(4, student.getLastName());
			statement.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
