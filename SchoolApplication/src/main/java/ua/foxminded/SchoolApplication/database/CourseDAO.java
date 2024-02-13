package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;

public class CourseDAO {
	public void primaryinsertCourses(List<Course> courses) {
		String sql = "INSERT INTO school_app.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Course course : courses) {
				statement.setInt(1, course.getCourseID());
				statement.setString(2, course.getCourseName());
				statement.setString(3, course.getCourseDescription());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
