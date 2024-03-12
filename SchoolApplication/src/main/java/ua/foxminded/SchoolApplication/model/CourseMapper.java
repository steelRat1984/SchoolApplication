package ua.foxminded.SchoolApplication.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper {
	public static Course map(ResultSet resultSet) throws SQLException {
		int courseId = resultSet.getInt("course_id");
		String courseName = resultSet.getString("course_name").trim();
		String courseDesctiption = resultSet.getString("course_description").trim();
		return new Course(courseId, courseName, courseDesctiption);
	}
}
