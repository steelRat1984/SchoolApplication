package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentRelationsDAO {
	
	
	
	public void addRelation(int studentId, int courseId) {
		String sql = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, studentId);
				statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getSelectedCoursesID(int studentId) {
		List<Integer> selectedCoursesID = new ArrayList<>();
		String sql = "SELECT course_id FROM school_app.students_courses_relations WHERE student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int courseId = resultSet.getInt("course_id");
					selectedCoursesID.add(courseId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return selectedCoursesID;
	}

	public void deleteAllRelationsByStudentId(int studentId) {
		String sqlDeleteRelation = "DELETE FROM school_app.students_courses_relations WHERE student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement deleteRelationStatement = connection.prepareStatement(sqlDeleteRelation)) {
			deleteRelationStatement.setInt(1, studentId);
			deleteRelationStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertRelation(Student student) {
		int studentId = student.getStudentID();
		String insertRelation = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
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

	public void primaryInsertRelations(List<Student> students) {
		String primaryInsertRelations = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(primaryInsertRelations)) {
			for (Student student : students) {
				int studentId = student.getStudentID();
				List<Course> currentCourses = student.getCourses();
				for (Course course : currentCourses) {
					int courseId = course.getCourseID();
					statement.setInt(1, studentId);
					statement.setInt(2, courseId);
					statement.addBatch();
				}
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
