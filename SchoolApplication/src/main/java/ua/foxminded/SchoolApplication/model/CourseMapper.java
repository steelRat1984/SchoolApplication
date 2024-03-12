package ua.foxminded.SchoolApplication.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.foxminded.SchoolApplication.database.CourseDAO;

public class CourseMapper {
	public static Course map(ResultSet resultSet) throws SQLException {
		int courseId = resultSet.getInt("course_id");
		String courseName = resultSet.getString("first_name");
		String courseDesctiption = resultSet.getString("last_name");
		int numberOfStudents = new CourseDAO().countStudentOnCourse(courseId);
		return new Course(courseId, numberOfStudents, courseName, courseDesctiption);
	}
}
