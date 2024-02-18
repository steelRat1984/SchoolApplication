package ua.foxminded.SchoolApplication.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import ua.foxminded.SchoolApplication.database.GroupDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.database.StudentRelationsDAO;
import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.DataGenerator;
import ua.foxminded.SchoolApplication.database.Database;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentServices {
	CourseDAO courseDAO = new CourseDAO();
	StudentDAO studentDAO = new StudentDAO();
	GroupDAO groupDAO = new GroupDAO();
	StudentRelationsDAO studentRelationsDAO = new StudentRelationsDAO();

	Connection connection = Database.connection();
	
	
	// ТАК САМО ЗРОБИТИ ВИДАЛЕННЯ СТУДЕНТА З КУРСУ
	public void enrollStudentToCourse(int studentId, int courseId) {
		Student student = studentDAO.getStudentById(studentId);
		Course course = courseDAO.getCourseById(courseId);
		List <Course> selectedCourses = student.getCourses();
		for (Course selectedCourse : selectedCourses) {
			if (selectedCourse.getCourseID() != course.getCourseID()) {
				selectedCourses.add(course);
				student.setCourses(selectedCourses);
				studentRelationsDAO.addRelation(studentId, courseId);
			}else {
				System.out.println("This student is alredy studying on this course");
			}
		}

	}
	
	
	
	
	
	
	
	public void deleteStudentById (int studentId) {
		studentDAO.deleteStudentById(studentId);
		studentRelationsDAO.deleteAllRelationsByStudentId(studentId);
	}

	public void IsertStudent (String firstName, String lastName ) {
		Random random = new Random();
		int groupId = random.nextInt(groupDAO.selectActualListOfGroups().size() + 1);
		int studentId = studentDAO.getMaximumStudentId() + 1;
		Group group = groupDAO.getGroupById(groupId);
		List <Course> courses = CourseServices.cutCourseListRandomly(courseDAO.selectActualCourseList());
		Student student = new Student(studentId, group, firstName, lastName, courses);
		studentDAO.inserntTheStudent(student);
		studentRelationsDAO.insertRelation(student);
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
