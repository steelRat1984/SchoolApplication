package ua.foxminded.SchoolApplication.schoolController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.database.Database;
import ua.foxminded.SchoolApplication.model.CourseName;
import ua.foxminded.SchoolApplication.model.Student;

public class CourseFillingReporter {
	private final Connection connection = Database.connection();

	public Map<Integer, CourseName> createCoursesMap() {
		Map<Integer, CourseName> courses = new HashMap<>();
		courses.put(1, CourseName.ART);
		courses.put(2, CourseName.BIOLOGY);
		courses.put(3, CourseName.CHEMISTRY);
		courses.put(4, CourseName.COMPUTER_SCIENCE);
		courses.put(5, CourseName.GEOGRAPHY);
		courses.put(6, CourseName.HISTORY);
		courses.put(7, CourseName.LITERATURE);
		courses.put(8, CourseName.MUSIC);
		courses.put(9, CourseName.PHYSICAL_EDUCATION);
		courses.put(10, CourseName.PHYSICS);
		return courses;
	}

	public String getNamesFromCourse(CourseName enumNames) {
		String courseName = enumNames.getName();
		int courseID = selectCourseID(courseName);
		List<Integer> studentsIds = selectStudentIds(courseID);
		List<Student> students = selectStudents(studentsIds);
		String studentsFullNames = writeStudentsFullNames(students);
		String result = createResultForPrint(studentsFullNames, courseName);
		return result;
	}

	private int selectCourseID(String courseName) {
		String sql = "SELECT course_id FROM school_app.courses WHERE course_name = ?";
		int courseId = 0;
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, courseName);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					courseId = resultSet.getInt("course_id");
					return courseId;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseId;
	}

	private List<Integer> selectStudentIds(int courseId) {
		List<Integer> studentIds = new ArrayList<>();
		String sql = "SELECT student_id FROM school_app.students_courses_relations WHERE course_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, courseId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int studentId = resultSet.getInt("student_id");
					studentIds.add(studentId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentIds;
	}

	private List<Student> selectStudents(List<Integer> studentIds) {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE student_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			for (int student_id : studentIds) {
				statement.setInt(1, student_id);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						Student student = new Student();
						int studentId = resultSet.getInt("student_id");
						int groupId = resultSet.getInt("group_id");
						String firstNname = resultSet.getString("first_name");
						String lastName = resultSet.getString("last_name");
						student.setStudentID(studentId);
						student.setGroupID(groupId);
						student.setFirstName(firstNname);
						student.setLastName(lastName);
						students.add(student);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	private String writeStudentsFullNames(List<Student> students) {
		StringBuilder studentsFullInfo = new StringBuilder();
		for (Student student : students) {
			int studentId = student.getStudentID();
			String firstName = student.getFirstName().trim();
			String lastName = student.getLastName().trim();
			studentsFullInfo.append("id: " + studentId);
			studentsFullInfo.append(" ");
			studentsFullInfo.append(firstName);
			studentsFullInfo.append(" ");
			studentsFullInfo.append(lastName);
			studentsFullInfo.append("\n");
		}

		return studentsFullInfo.toString();
	}

	private String createResultForPrint(String studentsFullInfo, String courseName) {
		String text = "List of student from" + " " + courseName + " course :";
		StringBuilder result = new StringBuilder();
		result.append(text);
		result.append("\n");
		result.append(studentsFullInfo);
		return result.toString();

	}

}
