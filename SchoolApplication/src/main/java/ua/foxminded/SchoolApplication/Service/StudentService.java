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

	public Student getStudentnByName(String firstName, String lastName) {
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
			return true;
		}
		return false;
	}

	public boolean deleteStudentFromCourse(int studentId, int courseId) {
		Student student = studentDAO.getStudentById(studentId);
		List<Course> selectedCourses = student.getCourses();
		boolean isDelated = false;
		Iterator<Course> iterator = selectedCourses.iterator();
		while (iterator.hasNext()) {
			Course selectedToDeleteCourse = iterator.next();
			if (selectedToDeleteCourse.getCourseID() == courseId) {
				iterator.remove();
				studentDAO.deleteRelation(studentId, courseId);
				isDelated = true;
				break;
			}
		}
		return isDelated;
	}

	public void deleteStudentById(int studentId) {
		studentDAO.deleteAllRelationsByStudentId(studentId);
		studentDAO.deleteStudentById(studentId);

	}

	public void insertStudent(Student inputStudent) {
		Random random = new Random();
		int groupId = random.nextInt(groupDAO.selectAllGroups().size() + 1);
		int studentId = studentDAO.getMaximumStudentId() + 1;
		Group group = groupDAO.getGroupById(groupId);
		List<Course> courses = DataGenerator.cutCourseListRandomly(courseDAO.selectAllCourses());
		Student student = new Student(studentId, group, inputStudent.getFirstName(), inputStudent.getLastName(),
				courses);
		studentDAO.inserntTheStudent(student);
		studentDAO.insertRelation(student);
	}
}