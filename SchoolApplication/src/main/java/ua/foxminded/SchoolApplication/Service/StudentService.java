package ua.foxminded.SchoolApplication.Service;

import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.DAO.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentService {
	private StudentDAO studentDAO = new StudentDAO();
	
	public Student getStudentByName(String firstName, String lastName) {
		return studentDAO.getStudentByName(firstName, lastName);
	}

	public Student getStudentById(int studentId) {
		return studentDAO.getStudentById(studentId);
	}

	public boolean enrollStudentToCourse(Student student, Course inputCourse) {
		List<Course> selectedCourses = student.getCourses();
		boolean isEnroled = selectedCourses.stream().anyMatch(course -> course.getCourseID() == inputCourse.getCourseID());
		if (!isEnroled) {
			List<Course> newCourse = new ArrayList<>();
			newCourse.add(inputCourse);
			student.setCourses(newCourse);
			studentDAO.createAssignment(student);
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
	
	public void createAssignment (Student student) {
		studentDAO.createAssignment(student);
	}
}
