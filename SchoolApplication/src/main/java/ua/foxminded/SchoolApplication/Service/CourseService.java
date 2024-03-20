package ua.foxminded.SchoolApplication.Service;

import java.util.List;

import ua.foxminded.SchoolApplication.DAO.CourseDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class CourseService {
	private CourseDAO courseDAO = new CourseDAO();
	
	public Course getCourseById(int courseId) {
		return courseDAO.getCourseById(courseId);	
	}
	
	public List<Course> getAllCourses(){
		return  courseDAO.getAllCourses();
	}
	
	public List<Student> getDataForCertainCourseReport(int courseId) {
		return courseDAO.getStudentsOnCourse(courseId);
	}
	
	public void createCourse (Course course) {
		courseDAO.createCourse(course);
	}
}
