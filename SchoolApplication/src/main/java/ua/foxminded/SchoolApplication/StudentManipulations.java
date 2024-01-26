package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StudentManipulations {
	Connection connection = Database.connection();

	public void CreateAndInsertStudent(Student student) {
		DataGenerate dataGenerate = new DataGenerate();
		DataBaseFiller dataBaseFiller = new DataBaseFiller();
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
		dataBaseFiller.fillingRelations(newRelations);
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

	public void addStudentToCourse(int studentId, CourseName EnumCourseName) {
		String courseName = EnumCourseName.getName();
		String sqlInsertRelations = "INSERT INTO school_app.students_courses_relations (student_id, course_id) VALUES (?, ?)";
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
								try (ResultSet resultSet = checkDuplecateStatement.executeQuery()) {
									if (resultSet.next() && resultSet.getInt(1) == 0) {
										try (PreparedStatement insertRelationsStatement = connection
												.prepareStatement(sqlInsertRelations)) {
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

}
