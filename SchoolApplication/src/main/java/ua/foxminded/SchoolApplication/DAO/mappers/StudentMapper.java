package ua.foxminded.SchoolApplication.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.DAO.CourseDAO;
import ua.foxminded.SchoolApplication.DAO.GroupDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentMapper {

	public static Student map(ResultSet resultSet) throws SQLException {
		int studentId = resultSet.getInt("student_id");
		String firstName = resultSet.getString("first_name").trim();
		String lastName = resultSet.getString("last_name").trim();
		int groupId = resultSet.getInt("group_id");
		Group group = new GroupDAO().getGroupById(groupId);
		List<Course> courses = new CourseDAO().getSelectedCoursesForStudent(studentId);
		return new Student(studentId, group, firstName, lastName, courses);
	}
}
