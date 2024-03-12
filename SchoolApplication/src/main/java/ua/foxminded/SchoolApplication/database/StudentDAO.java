package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.model.StudentMapper;

public class StudentDAO {
	private GroupDAO groupDAO = new GroupDAO();
	private CourseDAO courseDAO = new CourseDAO();

	public List<Student> getGroupEnrolledStudents(int groupId) {
		String sql = "SELECT student_id, first_name, last_name, group_id FROM school_app.students WHERE group_id = ?";
		List<Student> enrolledStudents = new ArrayList<>();
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, groupId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					enrolledStudents.add(StudentMapper.map(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrolledStudents;
	}

	public List<Student> getCourseEnrolledStudents(int courseId) {
		String sql = "SELECT s.student_id, s.first_name, s.last_name, s.group_id FROM school_app.students s "
				+ "JOIN school_app.students_courses sc ON s.student_id = sc.student_id WHERE sc.course_id = ?";
		List<Student> enrolledStudents = new ArrayList<>();
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					enrolledStudents.add(StudentMapper.map(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrolledStudents;
	}

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

	public void createOneStudent(Student student) {
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
		String sql = "SELECT student_id, group_id, first_name, last_name FROM school_app.students";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				allStudents.add(StudentMapper.map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allStudents;
	}

	public List<Integer> getStudentsIdByCourseId(int courseid) {
		List<Integer> studentIds = new ArrayList<>();
		String sql = "SELECT student_id FROM school_app.students_courses WHERE course_id = ?";
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

	public Integer deleteRelation(int studentId, int courseId) {
		int executionRequest = 0;
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ? AND course_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			executionRequest = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return executionRequest;
	}

	public void addRelation(int studentId, int courseId) {
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

	public void deleteAllRelationsByStudentId(int studentId) {
		String sqlDeleteRelation = "DELETE FROM school_app.students_courses WHERE student_id = ?";
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

	public void primaryInsertRelations(List<Student> students) {
		String primaryInsertRelations = "INSERT INTO school_app.students_courses (student_id, course_id) VALUES (?, ?)";
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
