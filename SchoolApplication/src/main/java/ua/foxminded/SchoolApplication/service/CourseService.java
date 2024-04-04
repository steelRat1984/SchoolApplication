package ua.foxminded.SchoolApplication.service;

import java.util.List;

import ua.foxminded.SchoolApplication.dao.CourseDAO;
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
	
	public List<Student> getStudentsOnCourse(int courseId) {
		return courseDAO.getStudentsOnCourse(courseId);
	}
	
	public void createCourse (Course course) {
		courseDAO.createCourse(course);
	}
}