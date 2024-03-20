package ua.foxminded.SchoolApplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.DAO.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentDAO {

	public Student getStudentByName(String firstName, String lastName) {
		Student student = new Student();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE first_name = ? AND last_name = ?";
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
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE student_id = ?";
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
		String sqlDeleteStudent = "DELETE FROM school_app.students WHERE student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement deleteStudentStatement = connection.prepareStatement(sqlDeleteStudent)) {
			deleteStudentStatement.setInt(1, studentId);
			deleteStudentStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createStudent(Student student) {
		String sql = "INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
		int groupId = student.getGroup().getGroupID();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, groupId);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteAssignments(int studentId, int courseId) {
		boolean isDeleted = false;
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ? AND course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			isDeleted = statement.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	public void assignCourse(int studentId, int courseId) {
		String sql = "INSERT INTO school_app.students_courses (student_id, course_id) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllAssignmentsByStudentId(int studentId) {
		String sqlDeleteRelation = "DELETE FROM school_app.students_courses WHERE student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement deleteRelationStatement = connection.prepareStatement(sqlDeleteRelation)) {
			deleteRelationStatement.setInt(1, studentId);
			deleteRelationStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createAssignment(Student student) {
		int studentId = student.getStudentID();
		String insertRelation = "INSERT INTO school_app.students_courses (student_id, course_id) VALUES (?, ?)";
		List<Course> currentCourses = student.getCourses();
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(insertRelation)) {
			for (Course course : currentCourses) {
				int courseId = course.getCourseID();
				statement.setInt(1, studentId);
				statement.setInt(2, courseId);
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
