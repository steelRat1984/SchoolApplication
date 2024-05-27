package ua.foxminded.SchoolApplication.service;

import java.util.List;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.dao.CourseDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@RequiredArgsConstructor
@Service
public class CourseService {
	private final CourseDAO courseDAO;

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
