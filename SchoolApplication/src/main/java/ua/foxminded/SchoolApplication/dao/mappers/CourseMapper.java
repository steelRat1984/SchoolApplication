package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.model.Course;

@Component
public class CourseMapper implements RowMapper<Course>{

	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		int courseId = rs.getInt("course_id");
		String courseName = rs.getString("course_name").trim();
		String courseDesctiption = rs.getString("course_description").trim();
		return new Course(courseId, courseName, courseDesctiption);
	}
}
