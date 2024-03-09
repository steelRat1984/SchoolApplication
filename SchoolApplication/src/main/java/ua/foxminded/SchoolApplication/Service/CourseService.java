package ua.foxminded.SchoolApplication.Service;

import java.util.List;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;

public class CourseService {
	CourseDAO courseDAO = new CourseDAO();
	StudentDAO studentDAO = new StudentDAO();
	
	public Course getCourseById(int courseid) {
		Course course = courseDAO.getCourseById(courseid);
		return course;	
	}
	
	public List<Course> getCourses(){
		List<Course> coursesList = courseDAO.selectAllCourses();
		return coursesList;
	}
}
