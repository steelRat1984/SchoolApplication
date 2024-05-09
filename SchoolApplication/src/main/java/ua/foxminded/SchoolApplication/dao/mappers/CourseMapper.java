package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.SchoolApplication.model.Course;

public class CourseMapper implements RowMapper<Course>{
	
	
	
	public static Course map(ResultSet resultSet) throws SQLException {
		int courseId = resultSet.getInt("course_id");
		String courseName = resultSet.getString("course_name").trim();
		String courseDesctiption = resultSet.getString("course_description").trim();
		return new Course(courseId, courseName, courseDesctiption);
	}

	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		int courseId = rs.getInt("course_id");
		String courseName = rs.getString("course_name").trim();
		String courseDesctiption = rs.getString("course_description").trim();
		return new Course(courseId, courseName, courseDesctiption);
	}
}
