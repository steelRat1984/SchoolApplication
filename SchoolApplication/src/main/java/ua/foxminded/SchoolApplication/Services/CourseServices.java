package ua.foxminded.SchoolApplication.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;
public class CourseServices {
	CourseDAO courseDAO = new CourseDAO();
	StudentDAO studentDAO = new StudentDAO();
	
	public Course getCourseById(int courseid) {
		Course course = courseDAO.getCourseById(courseid);
		return course;	
	}
	
	public List<Course> getCoursesList(){
		List<Course> coursesList = courseDAO.selectActualCourseList();
		return coursesList;
	}
	public Map <Course,List<Student>> getDataForCertainCourseReport(int courseId ) {
		Course course = courseDAO.getCourseById(courseId);
		List<Integer> enrolledstudentsId = studentDAO.getStudentsIdByCourseId(courseId);
		List<Student> enrolledStudents = new ArrayList<>();
		Map <Course,List<Student>> courseReport = new HashMap<>();
		for (int studentId : enrolledstudentsId ) {
			Student student = studentDAO.getStudentById(studentId);
			enrolledStudents.add(student);
		}
		courseReport.put(course, enrolledStudents);
		return courseReport;
	}
	
	public List<Course> getSelectedCoursesforStudent(int studentId) {
		List<Integer> selectedCourseId = studentDAO.getSelectedCoursesID(studentId);
		List<Course> actualCourseList = courseDAO.selectActualCourseList();
		List<Course> selectedCourses = new ArrayList<>();
		for (int courseId : selectedCourseId) {
			for (Course course : actualCourseList) {
				if (course.getCourseID() == courseId) {
					selectedCourses.add(course);
					break;
				}
			}
		}
		return selectedCourses;
	}

	public static List<Course> cutCourseListRandomly(List<Course> fullListCourses) {
		Random random = new Random();
		int amountCourses = random.nextInt(3) + 1;
		List<Course> cutListOfCourses = new ArrayList<>();
		List<Course> tempFullList = new ArrayList<>(fullListCourses);
		for (int i = 0; i < amountCourses; i++) {
			int eaachCourseIndex = random.nextInt(tempFullList.size());
			cutListOfCourses.add(tempFullList.get(eaachCourseIndex));
			tempFullList.remove(eaachCourseIndex);
		}
		return cutListOfCourses;
	}
}
