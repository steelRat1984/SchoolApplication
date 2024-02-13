package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentDAO {
	public void primaryInsertsStudents(List<Student> students) {
		String sql = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
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

	public void primaryInsertRelations(List<Student> students) {
		String sql = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Student student : students) {
				List<Course> currentCourses = student.getCourses();
				for (Course course : currentCourses) {
				statement.setInt(1, student.getStudentID());
				statement.setInt(2, course.getCourseID());
				statement.addBatch();
				}
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
