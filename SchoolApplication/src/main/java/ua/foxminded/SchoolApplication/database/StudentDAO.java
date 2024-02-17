package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentDAO {

	public static void inserntTheStudent (Student student){
		String insertStudent = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		int studentId = student.getStudentID();
		int groupId = student.getGroup().getGroupID();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(insertStudent)) {
			statement.setInt(1, studentId);
			statement.setInt(2, groupId);
			statement.setString(3, firstName);
			statement.setString(4, lastName);
			statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int getMaximumStudentId() {
		String SelectMaxID = "SELECT MAX(student_id) FROM school_app.students";
		int maxStudentID = 0;
		try ( Connection connection = Database.connection();
				PreparedStatement selectStatement = connection.prepareStatement(SelectMaxID)) {
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				  maxStudentID = resultSet.getInt(1);
			}
			maxStudentID = resultSet.getInt(1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return maxStudentID;
	}
	public void primaryInsertsStudents(List<Student> students) {
		String primaryInsertsStudents = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(primaryInsertsStudents)) {
			for (Student student : students) {
				statement.setInt(1, student.getStudentID());
				statement.setInt(2, student.getGroup().getGroupID());
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
