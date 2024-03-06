package ua.foxminded.SchoolApplication.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

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
	public Map<Course,List<Student>> getDataForCertainCourseReport(int courseId ) {
		Course course = courseDAO.getCourseById(courseId);
		List<Integer> enrolledstudentsId = studentDAO.getStudentsIdByCourseId(courseId);
		List<Student> enrolledStudents = new ArrayList<>();
		Map<Course,List<Student>> courseReport = new HashMap<>();
		for (int studentId : enrolledstudentsId ) {
			Student student = studentDAO.getStudentById(studentId);
			enrolledStudents.add(student);
		}
		courseReport.put(course, enrolledStudents);
		return courseReport;
	}
	
	public List<Course> getSelectedCoursesforStudent(int studentId) {
		List<Integer> selectedCourseId = studentDAO.getSelectedCoursesID(studentId);
		List<Course> allCourses = courseDAO.selectAllCourses();
		List<Course> selectedCourses = new ArrayList<>();
		for (int courseId : selectedCourseId) {
			for (Course course : allCourses) {
				if (course.getCourseID() == courseId) {
					selectedCourses.add(course);
					break;
				}
			}
		}
		return selectedCourses;
	}
}