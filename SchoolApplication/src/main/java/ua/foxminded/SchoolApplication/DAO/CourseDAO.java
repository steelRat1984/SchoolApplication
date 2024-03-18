package ua.foxminded.SchoolApplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.CourseMapper;
import ua.foxminded.SchoolApplication.model.Group;
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

	public Course getCourseById(int inputcourseId) {
		Course course = new Course();
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses WHERE course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, inputcourseId);
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
		String sql = "SELECT s.student_id, s.first_name, s.last_name, s.group_id " +
				"FROM school_app.students s " +
				"JOIN school_app.students_courses sc ON s.student_id = sc.student_id " +
				"WHERE sc.course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = new Student();
					student.setStudentID(resultSet.getInt("student_id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					int groupId = resultSet.getInt("group_id");
					Group group = new GroupDAO().getGroupById(groupId);
					student.setGroup(group);
					students.add(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public void primaryCourseCreation(List<Course> courses) {
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
