package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.Services.CourseServices;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentDAO {

	public Student getStudentByName(String firstName, String lastName) {
		GroupDAO groupDAO = new GroupDAO();
		CourseServices courseServices = new CourseServices();
		Student student = new Student();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE first_name = ? AND last_name = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int studentId = resultSet.getInt("student_id");
					int groupId = resultSet.getInt("group_id");
					String studentFirstName = resultSet.getString("first_name").trim();
					String studentLastName = resultSet.getString("last_name").trim();
					student.setStudentID(studentId);
					student.setGroup(groupDAO.getGroupById(groupId));
					student.setFirstName(studentFirstName);
					student.setLastName(studentLastName);
					student.setCourses(courseServices.getSelectedCoursesforStudent(studentId));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public Student getStudentById(int studentId) {
		GroupDAO groupDAO = new GroupDAO();
		CourseServices courseServices = new CourseServices();
		Student student = new Student();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students WHERE student_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int groupId = resultSet.getInt("group_id");
					student.setGroup(groupDAO.getGroupById(groupId));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					student.setCourses(courseServices.getSelectedCoursesforStudent(studentId));
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

	public void inserntTheStudent(Student student) {
		String insertStudent = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		int studentId = student.getStudentID();
		int groupId = student.getGroup().getGroupID();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(insertStudent)) {
			statement.setInt(1, studentId);
			statement.setInt(2, groupId);
			statement.setString(3, firstName);
			statement.setString(4, lastName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getMaximumStudentId() {
		String SelectMaxID = "SELECT MAX(student_id) FROM school_app.students";
		int maxStudentID = 0;
		try (Connection connection = Database.connection();
				PreparedStatement selectStatement = connection.prepareStatement(SelectMaxID)) {
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				maxStudentID = resultSet.getInt(1);
			}
			maxStudentID = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxStudentID;
	}

	public void primaryInsertsStudents(List<Student> students) {
		String primaryInsertsStudents = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(primaryInsertsStudents)) {
			for (Student student : students) {
				statement.setInt(1, student.getStudentID());
				statement.setInt(2, student.getGroup().getGroupID());
				statement.setString(3, student.getFirstName());
				statement.setString(4, student.getLastName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Student> getAllStudents() {
		List<Student> allStudents = new ArrayList<>();
		GroupDAO groupDAO = new GroupDAO();
		CourseServices courseServices = new CourseServices();
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students";

		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Student student = new Student();
				int studentId = resultSet.getInt("student_id");
				int groupId = resultSet.getInt("group_id");

				student.setStudentID(studentId);
				student.setGroup(groupDAO.getGroupById(groupId));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				student.setCourses(courseServices.getSelectedCoursesforStudent(studentId));

				allStudents.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allStudents;
	}

	public List<Integer> getStudentsIdByCourseId(int courseid) {
		List<Integer> studentIds = new ArrayList<>();
		String sql = "SELECT student_id FROM school_app.students_courses_relations WHERE course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, courseid);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

	public void deleteRelation(int studentId, int courseId) {
		String sql = "DELETE FROM school_app.students_courses_relations WHERE student_id = ? AND course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
