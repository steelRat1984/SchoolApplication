package ua.foxminded.SchoolApplication;

import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataBaseAccess {
	Connection connection = Database.connection();
	
	
// TEST filling in student data
//	public static void main(String[] args) {
//		DataBaseAccess dataBaseAccess = new DataBaseAccess();
//		DataGenerate dataGenerate = new DataGenerate();
//		List<Student> students = dataGenerate.generateStudents();
//		dataBaseAccess.filingStudentsData(students);
//	}
	Database database = new Database();

	
	public void fillingGroupData(List<Group>groups) {
		String sql = "INSERT INTO sclool_app.students (group_id, group_name) VALUES (?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (Group group : groups ) {
				statement.setInt(1, group.getGroupID());
				statement.setString(2, group.getGroupName());
				statement.addBatch();
			}
			statement.executeBatch();
			statement.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void fillingStudentsData(List<Student> students) {
		String sql = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
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
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
