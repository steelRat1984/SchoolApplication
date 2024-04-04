package ua.foxminded.SchoolApplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.dao.mappers.CourseMapper;
import ua.foxminded.SchoolApplication.dao.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class CourseDAO {
	
	public List<Course> getSelectedCoursesForStudent(int studentId) {
		String sql = "SELECT c.course_id, c.course_name, c.course_description FROM school_app.courses c "
				+ "JOIN school_app.students_courses sc ON c.course_id = sc.course_id WHERE sc.student_id = ?";
		List<Course> selectedCourses = new ArrayList<>();
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
		Course course = new Course();
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses WHERE course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					course = CourseMapper.map(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	public List<Course> getAllCourses() {
		List<Course> allCourses = new ArrayList<>();
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				allCourses.add(CourseMapper.map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allCourses;
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
				students = StudentMapper.mapStudents(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public void createCourse(Course course) {
		String sql = "INSERT INTO school_app.courses (course_name, course_description) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, course.getCourseName());
				statement.setString(2, course.getCourseDescription());
				statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
