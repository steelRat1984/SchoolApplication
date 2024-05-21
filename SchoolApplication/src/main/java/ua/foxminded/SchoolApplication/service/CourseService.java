package ua.foxminded.SchoolApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.SchoolApplication.dao.CourseDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Service
public class CourseService {
	private final CourseDAO courseDAO;
	
	@Autowired
	public CourseService(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

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
