package ua.foxminded.SchoolApplication.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.DataGenerator;
import ua.foxminded.SchoolApplication.database.GroupDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentService {
	CourseDAO courseDAO = new CourseDAO();
	StudentDAO studentDAO = new StudentDAO();
	GroupDAO groupDAO = new GroupDAO();
	
	public List<Student> getDataForCertainCourseReport(int courseId ) {
		List<Student> enrolledStudents = studentDAO.getCourseEnrolledStudents(courseId);
		return enrolledStudents;
	}
	
	public Student getStudentByName(String firstName, String lastName) {
		Student student = studentDAO.getStudentByName(firstName, lastName);
		return student;
	}

	public Student getStudentById(int studentId) {
		Student student = studentDAO.getStudentById(studentId);
		return student;
	}

	public boolean enrollStudentToCourse(int studentId, int courseId) {
		Student student = studentDAO.getStudentById(studentId);
		List<Course> selectedCourses = student.getCourses();
		boolean isEnroled = selectedCourses.stream().anyMatch(course -> course.getCourseID() == courseId);
		if (!isEnroled) {
			Course course = courseDAO.getCourseById(courseId);
			selectedCourses.add(course);
			student.setCourses(selectedCourses);
			studentDAO.addRelation(studentId, courseId);
			return true;
		}
		return false;
	}

	public boolean deletedStudentFromCourse(int studentId, int courseId) {
		int executionRequest = studentDAO.deleteRelation(studentId, courseId);
		boolean isDeleted = false ;
		if (executionRequest > 0) {
			isDeleted = true;
		}
		return isDeleted;
	}

	public void deleteStudentById(int studentId) {
		studentDAO.deleteAllRelationsByStudentId(studentId);
		studentDAO.deleteStudentById(studentId);

	}

	public void createStudent(Student inputStudent) {
		Random random = new Random();
		int groupId = random.nextInt(groupDAO.selectAllGroups().size());
		int studentId = studentDAO.getMaximumStudentId() + 1;
		Group group = groupDAO.getGroupById(groupId);
		List<Course> courses = DataGenerator.cutCourseListRandomly(courseDAO.getAllCourses());
		Student student = new Student(studentId, group, inputStudent.getFirstName(), inputStudent.getLastName(),
				courses);
		studentDAO.inserntOneStudent(student);
		studentDAO.insertRelation(student);
	}
}
