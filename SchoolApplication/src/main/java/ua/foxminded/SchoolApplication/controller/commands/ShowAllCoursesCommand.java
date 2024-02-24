package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;

import ua.foxminded.SchoolApplication.Services.CourseServices;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;

public class ShowAllCoursesCommand implements Command {
	private CourseServices courseServices = new CourseServices();

	public ShowAllCoursesCommand(CourseServices courseServices) {
		this.courseServices = courseServices;
	}

	@Override
	public void execute() {
		System.out.println("Here is the list of all courses that are available at the moment :");
		List<Course> courses = courseServices.getCoursesList();
		for (Course course : courses) {
			System.out.println(
					course.getCourseID() + ". " + course.getCourseName().trim() + ", " + course.getCourseDescription().trim());
		}
	}
}
