package ua.foxminded.SchoolApplication.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentMapper {

	public static List<Student> mapStudents(ResultSet resultSet) throws SQLException {
		List<Student> students = new ArrayList<>();
		Student student = null;
		int lastStudentId = -1;

		while (resultSet.next()) {
			int currentStudentId = resultSet.getInt("student_id");
			if (currentStudentId != lastStudentId) {
				if (student != null) {
					students.add(student);
				}
				String firstName = resultSet.getString("first_name").trim();
				String lastName = resultSet.getString("last_name").trim();
				int groupId = resultSet.getInt("group_id");
				String groupName = resultSet.getString("group_name").trim();
				Group group = new Group(groupId, groupName);
				student = new Student(currentStudentId, group, firstName, lastName, new ArrayList<>());
				lastStudentId = currentStudentId;
			}
			int courseId = resultSet.getInt("course_id");
			if (!resultSet.wasNull()) {
				String courseName = resultSet.getString("course_name").trim();
				String courseDescription = resultSet.getString("course_description").trim();
				Course course = new Course(courseId, courseName, courseDescription);
				student.getCourses().add(course);
			}
		}
		if (student != null) {
			students.add(student);
		}

		return students;
	}
}
