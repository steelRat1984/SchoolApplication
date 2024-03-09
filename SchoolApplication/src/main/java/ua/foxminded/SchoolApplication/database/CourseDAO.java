package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.Service.CourseService;
import ua.foxminded.SchoolApplication.model.Course;

public class CourseDAO {
	private CourseService courseService = new CourseService();

	public List<Course> getSelectedCoursesForStudent(int studentId) {
		String sql = "SELECT c.course_id, c.course_name, c.course_description FROM school_app.courses c "
				+ "JOIN school_app.students_courses sc ON c.course_id = sc.course_id WHERE sc.student_id = ?";
		List<Course> selectedCourses = new ArrayList<>();
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int courseId = resultSet.getInt("course_id");
					int numberOfStudents = courseService.countStudentsByCourse(courseId);
					String courseName = resultSet.getString("course_name").trim();
					String courseDescription = resultSet.getString("course_description").trim();
					Course course = new Course(courseId, numberOfStudents, courseName, courseDescription);
					selectedCourses.add(course);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectedCourses;
	}

	public Course getCourseById(int InputcourseId) {
		Course course = new Course();
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses WHERE course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, InputcourseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String courseName = resultSet.getString("course_name").trim();
					String courseDescription = resultSet.getString("course_description").trim();
					int courseId = resultSet.getInt("course_id");
					int numberOfStudents = courseService.countStudentsByCourse(courseId);
					course = new Course(courseId, numberOfStudents, courseName, courseDescription);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	public List<Course> selectAllCourses() {
		List<Course> allCourses = new ArrayList<>();
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int courseId = resultSet.getInt("course_id");
				int numberOfStudents = courseService.countStudentsByCourse(courseId);
				String courseName = resultSet.getString("course_name");
				String courseDescription = resultSet.getString("course_description");
				Course course = new Course(courseId, numberOfStudents, courseName, courseDescription);
				allCourses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allCourses;
	}

	public void primaryGenerationCourses(List<Course> courses) {
		String sql = "INSERT INTO school_app.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Course course : courses) {
				statement.setInt(1, course.getCourseID());
				statement.setString(2, course.getCourseName());
				statement.setString(3, course.getCourseDescription());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
