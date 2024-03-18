package ua.foxminded.SchoolApplication.Service;

import java.util.List;

import ua.foxminded.SchoolApplication.DAO.CourseDAO;
import ua.foxminded.SchoolApplication.DAO.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentService {
	private CourseDAO courseDAO = new CourseDAO();
	private StudentDAO studentDAO = new StudentDAO();
	
	public Student getStudentByName(String firstName, String lastName) {
		return studentDAO.getStudentByName(firstName, lastName);
	}

	public Student getStudentById(int studentId) {
		return studentDAO.getStudentById(studentId);
	}

	public boolean enrollStudentToCourse(int studentId, int courseId) {
		Student student = studentDAO.getStudentById(studentId);
		List<Course> selectedCourses = student.getCourses();
		boolean isEnroled = selectedCourses.stream().anyMatch(course -> course.getCourseID() == courseId);
		if (!isEnroled) {
			Course course = courseDAO.getCourseById(courseId);
			selectedCourses.add(course);
			student.setCourses(selectedCourses);
			studentDAO.assignCourse(studentId, courseId);
			return true;
		}
		return false;
	}

	public boolean deletedStudentFromCourse(int studentId, int courseId) {
		return studentDAO.deleteAssignments(studentId, courseId);
	}

	public void deleteStudentById(int studentId) {
		studentDAO.deleteAllAssignmentsByStudentId(studentId);
		studentDAO.deleteStudentById(studentId);
	}

	public void createStudent(Student inputStudent) {
		studentDAO.createStudent(inputStudent);
	}
}
