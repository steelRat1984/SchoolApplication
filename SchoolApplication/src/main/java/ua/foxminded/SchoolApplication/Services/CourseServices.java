package ua.foxminded.SchoolApplication.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.SchoolApplication.database.CourseDAO;
import ua.foxminded.SchoolApplication.database.StudentRelationsDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class CourseServices {
	CourseDAO courseDAO = new CourseDAO();

	public List<Course> getSelectedCourses(int studentId) {
		StudentRelationsDAO studentRelationsDAO = new StudentRelationsDAO();
		List<Integer> selectedCourseId = studentRelationsDAO.getSelectedCoursesID(studentId);
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
