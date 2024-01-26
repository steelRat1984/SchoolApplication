package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectorNamesFromCourse {
	public String selectNamesFromCourse(CourseName enumNames) {
		String courseName = enumNames.getName();
		int courseID = selectCourseID(courseName);
		List<Integer> studentsIds = selectStudentIds(courseID);
		List<Student> students = selectStudents(studentsIds);
		String studentsFullNames = writeStudentsFullNames(students);
		String result = createResultForPrint(studentsFullNames, courseName);
		return result;
	}

	private int selectCourseID(String course_name) {
		String sql = "SELECT course_id FROM school_app.courses WHERE course_name = ?";
		Connection connection = Database.connection();
		int course_id = 0;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, course_name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				course_id = resultSet.getInt("course_id");
				return course_id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course_id;
	}

	private List<Integer> selectStudentIds(int course_id) {
		List<Integer> studentIds = new ArrayList<>();
		String sql = "SELECT student_id FROM school_app.students_courses_relations WHERE course_id = ?";
		Connection connection = Database.connection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, course_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int student_id = resultSet.getInt("student_id");
				studentIds.add(student_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentIds;
	}

	private List<Student> selectStudents(List<Integer> studentIds) {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE student_id = ?";
		Connection connection = Database.connection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for (int student_id : studentIds) {
				preparedStatement.setInt(1, student_id);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Student student = new Student();
					int stidentId = resultSet.getInt("student_id");
					int groupId = resultSet.getInt("group_id");
					String firstNname = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");
					student.setStudentID(stidentId);
					student.setGroupID(groupId);
					student.setFirstName(firstNname);
					student.setLastName(lastName);
					students.add(student);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	private String writeStudentsFullNames(List<Student> students) {
		StringBuilder studentsFullNames = new StringBuilder();
		for (Student student : students) {
			String firstName = student.getFirstName().trim();
			String lastName = student.getLastName().trim();
			studentsFullNames.append(firstName);
			studentsFullNames.append(" ");
			studentsFullNames.append(lastName);
			studentsFullNames.append("\n");
		}

		return studentsFullNames.toString();
	}

	private String createResultForPrint(String studentsFullNames, String courseName) {
		String text = "List of student from" + " " + courseName + " course :";
		StringBuilder result = new StringBuilder();
		result.append(text);
		result.append("\n");
		result.append(studentsFullNames);
		return result.toString();

	}

}
