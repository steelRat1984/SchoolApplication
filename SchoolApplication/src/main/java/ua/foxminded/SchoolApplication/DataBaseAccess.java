package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataBaseAccess {
	
	
	
// TEST filling in student data
//	public static void main(String[] args) {
//		DataBaseAccess dataBaseAccess = new DataBaseAccess();
//		DataGenerate dataGenerate = new DataGenerate();
//		List<Student> students = dataGenerate.generateStudents();
//		dataBaseAccess.insertStudentsData(students);
//	}
	Database database = new Database();

	public void insertStudentsData(List<Student> students) {
		String sql = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		Connection connection = database.connection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (Student student : students) {
				statement.setInt(1, student.getStudentID());
				statement.setInt(2, student.getGroupID());
				statement.setString(3, student.getFirstName());
				statement.setString(4, student.getLastName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
