package ua.foxminded.SchoolApplication.Service;

import java.util.List;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class CourseService {
	CourseDAO courseDAO = new CourseDAO();
	StudentDAO studentDAO = new StudentDAO();
	
	public Integer countStudentsByCourse (int courseId) {
		List<Student> students = studentDAO.getEnrolledStudents(courseId);
		int result = students.size();
		return result;
	}
	
	public Course getCourseById(int courseId) {
		Course course = courseDAO.getCourseById(courseId);
		return course;	
	}
	
	public List<Course> getCourses(){
		List<Course> coursesList = courseDAO.selectAllCourses();
		return coursesList;
	}
}
