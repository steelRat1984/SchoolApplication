package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentRelationsDAO {

	public static void insertRelation (Student student ) {
		int studentId = student.getStudentID();
		String insertRelation = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		List <Course> currentCourses = student.getCourses();
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
