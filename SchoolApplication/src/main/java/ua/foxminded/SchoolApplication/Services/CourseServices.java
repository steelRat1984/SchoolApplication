package ua.foxminded.SchoolApplication.Services;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.SchoolApplication.model.Course;

public class CourseServices {
	
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
