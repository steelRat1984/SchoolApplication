package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class StudentManipulations {
	Connection connection = Database.connection();

	public void createStudent(Student student) {
		String SelectStudentId = "SELECT MAX(student_id) FROM school_app.students";
		int studentID = 0;
		Random random = new Random();
		int groupID = random.nextInt(10) + 1;

		String insertStudent = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try {

			PreparedStatement selectStatement = connection.prepareStatement(SelectStudentId);
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				studentID = resultSet.getInt(1) + 1;
			}
			student.setStudentID(studentID);
			student.setGroupID(groupID);
			PreparedStatement Insertstatement = connection.prepareStatement(insertStudent);
			Insertstatement.setInt(1, student.getStudentID());
			Insertstatement.setInt(2, student.getGroupID());
			Insertstatement.setString(3, student.getFirstName());
			Insertstatement.setString(4, student.getLastName());
			Insertstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
