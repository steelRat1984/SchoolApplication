package ua.foxminded.SchoolApplication.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.GroupDAO;

public class StudentMapper {

	public static Student map(ResultSet resultSet) throws SQLException {
		int studentId = resultSet.getInt("student_id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		int groupId = resultSet.getInt("group_id");
		Group group = new GroupDAO().getGroupById(groupId);
		List<Course> courses = new CourseDAO().getSelectedCoursesForStudent(studentId);
		return new Student(studentId, group, firstName, lastName, courses);
	}
}