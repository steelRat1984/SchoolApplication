package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

	public static List<Student> mapAllStudents(ResultSet resultSet) throws SQLException {
		Map<Integer, Student> idAndStudents = new TreeMap<>();
		while (resultSet.next()) {
			int studentId = resultSet.getInt("student_id");
			Student student = idAndStudents.get(studentId);

			if (student == null) {
				String firstName = resultSet.getString("first_name").trim();
				String lastName = resultSet.getString("last_name").trim();
				Group group = GroupMapper.map(resultSet);
				List<Course> courses = new ArrayList<>();
				student = new Student(studentId, group, firstName, lastName, courses);
				idAndStudents.put(studentId, student);
			}
			int courseId = resultSet.getInt("course_id");
			if (!resultSet.wasNull()) {
				Course course = CourseMapper.map(resultSet);
				student.getCourses().add(course);
			}
		}
		return new ArrayList<>(idAndStudents.values());
	}

}
