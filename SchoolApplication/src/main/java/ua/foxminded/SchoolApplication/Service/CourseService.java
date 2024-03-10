package ua.foxminded.SchoolApplication.Service;

import java.util.List;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.model.Course;

public class CourseService {
	private CourseDAO courseDAO = new CourseDAO();
	
	public Course getCourseById(int courseId) {
		Course course = courseDAO.getCourseById(courseId);
		return course;	
	}
	
	public List<Course> getAllCourses(){
		List<Course> coursesList = courseDAO.getAllCourses();
		return coursesList;
	}
}
