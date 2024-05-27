package ua.foxminded.SchoolApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.dao.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@RequiredArgsConstructor
@Service
public class StudentService {
	
	private final StudentDAO studentDAO;


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

	public boolean createStudent(Student inputStudent) {
		return studentDAO.createStudent(inputStudent);
	}
	
	public void createAssignment (Student student) {
		studentDAO.createAssignment(student);
	}
}
