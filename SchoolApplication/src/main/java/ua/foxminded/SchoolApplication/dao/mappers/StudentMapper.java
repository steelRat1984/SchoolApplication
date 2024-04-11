package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentMapper {

	public static Student map(ResultSet resultSet) throws SQLException {
		int studentId = resultSet.getInt("student_id");
		String firstName = resultSet.getString("first_name").trim();
		String lastName = resultSet.getString("last_name").trim();
		
		Group group = GroupMapper.map(resultSet);
		List<Course> courses = new ArrayList<>();
		while (resultSet.wasNull()) {
				courses.add(CourseMapper.map(resultSet));
		}
		return new Student(studentId, group, firstName, lastName, courses);

	}
}
