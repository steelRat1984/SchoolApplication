package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.model.StudentCourseRelation;

public class DatabaseInserter {
	Connection connection = Database.connection();

	protected void insertCourseData(List<Course> courses) {
		String sql = "INSERT INTO school_app.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

	protected void insertGroupData(List<Group> groups) {
		String sql = "INSERT INTO school_app.groups (group_id, group_name) VALUES (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Group group : groups) {
				statement.setInt(1, group.getGroupID());
				statement.setString(2, group.getGroupName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void insertStudentsData(List<Student> students) {
		String sql = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Student student : students) {
				statement.setInt(1, student.getStudentID());
				statement.setInt(2, student.getGroupID());
				statement.setString(3, student.getFirstName());
				statement.setString(4, student.getLastName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void insertRelations(List<StudentCourseRelation> relations) {
		String sql = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			for (StudentCourseRelation relation : relations) {
				statement.setInt(1, relation.getStudentID());
				statement.setInt(2, relation.getCourseID());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
