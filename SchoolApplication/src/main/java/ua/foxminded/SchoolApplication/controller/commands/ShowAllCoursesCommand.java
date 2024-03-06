package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;

import ua.foxminded.SchoolApplication.Service.CourseService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;

public class ShowAllCoursesCommand implements Command {
	private CourseService courseServices = new CourseService();

	public ShowAllCoursesCommand(CourseService courseServices) {
		this.courseServices = courseServices;
	}

	@Override
	public void execute() {
		System.out.println("Here is the list of all courses that are available at the moment :");
		List<Course> courses = courseServices.getCourses();
		for (Course course : courses) {
			System.out.println(
					course.getCourseID() + ". " + course.getCourseName().trim() + ", " + course.getCourseDescription().trim());
		}
	}

	@Override
	public String getDescription() {
		String description = "show all avaible courses";
		return description;
	}
}
