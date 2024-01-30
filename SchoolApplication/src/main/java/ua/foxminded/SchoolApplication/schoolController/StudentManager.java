package ua.foxminded.SchoolApplication.schoolController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.SchoolApplication.database.DatabaseInserter;
import ua.foxminded.SchoolApplication.database.DataGenerator;
import ua.foxminded.SchoolApplication.database.Database;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.CourseName;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.model.StudentCourseRelation;

public class StudentManager {
	Connection connection = Database.connection();

	public void CreateAndInsertStudent(Student student) {
		DataGenerator dataGenerate = new DataGenerator();
		DatabaseInserter dataBaseFiller = new DatabaseInserter();
		List<Student> students = new ArrayList<>();
		String SelectStudentId = "SELECT MAX(student_id) FROM school_app.students";
		int studentID = 0;
		Random random = new Random();
		int groupID = random.nextInt(10) + 1;
		String insertStudent = "INSERT INTO school_app.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement selectStatement = connection.prepareStatement(SelectStudentId);
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				studentID = resultSet.getInt(1) + 1;
			}
			student.setStudentID(studentID);
			student.setGroupID(groupID);
			PreparedStatement Insertstatement = connection.prepareStatement(insertStudent);
			Insertstatement.setInt(1, student.getStudentID());
			Insertstatement.setInt(2, student.getGroupID());
			Insertstatement.setString(3, student.getFirstName());
			Insertstatement.setString(4, student.getLastName());
			Insertstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Course> courses = dataGenerate.generateCourse();
		students.add(student);
		List<StudentCourseRelation> newRelations = dataGenerate.generateRelations(students, courses);
		dataBaseFiller.insertRelations(newRelations);
	}

	public void deleteStudentById(int studentId) {
		String sqlDeleteStudent = "DELETE FROM school_app.students WHERE student_id = ?";
		String sqlDeleteRelation = "DELETE FROM school_app.students_courses_relations WHERE student_id = ?";
		try {
			PreparedStatement deleteStudentStatement = connection.prepareStatement(sqlDeleteStudent);
			PreparedStatement deleteRelationStatement = connection.prepareStatement(sqlDeleteRelation);
			deleteStudentStatement.setInt(1, studentId);
			deleteRelationStatement.setInt(1, studentId);
			deleteRelationStatement.executeUpdate();
			deleteStudentStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addStudentToCourse(int studentId, CourseName enumCourseName) {
		String courseName = enumCourseName.getName();
		String insertRelations = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
		String selectCourseIdByName = "SELECT course_id FROM school_app.courses WHERE course_name = ?";
		String checkExistingCourses = "SELECT COUNT(*) FROM school_app.students_courses_relations WHERE student_id = ?";
		String checkCourseDuplication = "SELECT COUNT(*) FROM school_app.students_courses_relations WHERE student_id = ? AND course_id = ?";

		try (PreparedStatement existingStatement = connection.prepareStatement(checkExistingCourses)) {
			existingStatement.setInt(1, studentId);
			ResultSet existingResultSet = existingStatement.executeQuery();
			if (existingResultSet.next() && existingResultSet.getInt(1) < 3) {
				try (PreparedStatement selectCourseIdStatement = connection.prepareStatement(selectCourseIdByName)) {
					selectCourseIdStatement.setString(1, courseName);
					try (ResultSet selectCourseIdResultSet = selectCourseIdStatement.executeQuery()) {
						if (selectCourseIdResultSet.next()) {
							int courseId = selectCourseIdResultSet.getInt("course_id");
							try (PreparedStatement checkDuplecateStatement = connection
									.prepareStatement(checkCourseDuplication)) {
								checkDuplecateStatement.setInt(1, studentId);
								checkDuplecateStatement.setInt(2, courseId);
								try (ResultSet checkDuplecateResultSet = checkDuplecateStatement.executeQuery()) {
									if (checkDuplecateResultSet.next() && checkDuplecateResultSet.getInt(1) == 0) {
										try (PreparedStatement insertRelationsStatement = connection
												.prepareStatement(insertRelations)) {
											insertRelationsStatement.setInt(1, studentId);
											insertRelationsStatement.setInt(2, courseId);
											insertRelationsStatement.executeUpdate();
										}
									} else {
										System.out.println("This student is alredy studying on this course");
									}
								}
							}
						} else {
							System.out.println("Course not found");
						}
					}
				}
			} else {
				System.out.println("This student has more than 3 courses");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeStudentFromCourse(int studentId, CourseName enumCourseName) {
		String courseName = enumCourseName.getName();
		String selectCourseIdByName = "SELECT course_id FROM school_app.courses WHERE course_name = ?";
		String deleteRelations = "DELETE FROM school_app.students_courses_relations WHERE student_id = ? AND course_id = ?";
		String checkCourseExisting = "SELECT COUNT(*) FROM school_app.students_courses_relations WHERE student_id = ? AND course_id = ?";

		try (PreparedStatement selectCourseIdStatement = connection.prepareStatement(selectCourseIdByName)) {
			selectCourseIdStatement.setString(1, courseName);
			try (ResultSet selectCourseIdResultSet = selectCourseIdStatement.executeQuery()) {
				if (selectCourseIdResultSet.next()) {
					int courseId = selectCourseIdResultSet.getInt("course_id");
					try (PreparedStatement checkCourseExistingStatement = connection
							.prepareStatement(checkCourseExisting)) {
						checkCourseExistingStatement.setInt(1, studentId);
						checkCourseExistingStatement.setInt(2, courseId);
						try (ResultSet checkCourseExistingResultSet = checkCourseExistingStatement.executeQuery()) {
							if (checkCourseExistingResultSet.next() && checkCourseExistingResultSet.getInt(1) != 0) {
								try (PreparedStatement deleteRelationsStatement = connection
										.prepareStatement(deleteRelations)) {
									deleteRelationsStatement.setInt(1, studentId);
									deleteRelationsStatement.setInt(2, courseId);
									deleteRelationsStatement.executeUpdate();
								}
							} else {
								System.out.println("this student doesn't have this course");
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
