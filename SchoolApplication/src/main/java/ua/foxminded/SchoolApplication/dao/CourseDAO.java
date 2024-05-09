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

import ua.foxminded.SchoolApplication.dao.mappers.CourseMapper;
import ua.foxminded.SchoolApplication.dao.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Component
public class CourseDAO {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CourseDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Course> getSelectedCoursesForStudent(int studentId) {
		List<Course> selectedCourses = new ArrayList<>();
		String sql = "SELECT c.course_id, c.course_name, c.course_description FROM school_app.courses c "
				+ "JOIN school_app.students_courses sc ON c.course_id = sc.course_id WHERE sc.student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					selectedCourses.add(CourseMapper.map(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectedCourses;
	}

	public Course getCourseById(int courseId) {
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses WHERE course_id = ?";
		return jdbcTemplate.queryForObject(sql, new CourseMapper(), courseId);
	}

	public List<Course> getAllCourses() {
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses";
		return jdbcTemplate.query(sql, new CourseMapper());
	}


	public List<Student> getStudentsOnCourse(int courseId) {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE c.course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					students.add(StudentMapper.map(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public void createCourse(Course course) {
		String sql = "INSERT INTO school_app.courses (course_name, course_description) VALUES (?, ?)";
		jdbcTemplate.update(sql, course.getCourseName(), course.getCourseID());
	}

	
	

}
