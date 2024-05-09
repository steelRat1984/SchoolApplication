package ua.foxminded.SchoolApplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.dao.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Component
public class StudentDAO {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id ";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				students = StudentMapper.mapAllStudents(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public Student getStudentByName(String firstName, String lastName) {
		Student student = new Student();
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE s.first_name = ? AND s.last_name = ? "
				+ "ORDER BY c.course_id";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					student = StudentMapper.map(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public Student getStudentById(int studentId) {
		Student student = new Student();
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE s.student_id = ? "
				+ "ORDER BY c.course_id";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					student = StudentMapper.map(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public void deleteStudentById(int studentId) {
		String sql = "DELETE FROM school_app.students WHERE student_id = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public boolean createStudent(Student student) {
		String sql = "INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql,
				student.getGroup().getGroupID(),
				student.getFirstName(),
				student.getLastName()) > 0;

	}

	public boolean deleteAssignments(int studentId, int courseId) {
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ? AND course_id = ?";
		return jdbcTemplate.update(sql, studentId, courseId) > 0;
	}

	public void deleteAllAssignmentsByStudentId(int studentId) {
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public void createAssignment(Student student) {
		String sql = "INSERT INTO school_app.students_courses (student_id, course_id) VALUES (?, ?)";
		List<Course> courses = student.getCourses();
		List<Object[]> parameters = new ArrayList<>();
		for (Course course : courses) {
			parameters.add(new Object[] { student.getStudentID(), course.getCourseID() });
		}
		jdbcTemplate.batchUpdate(sql, parameters);
	}
}
