package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.service.CourseService;

@Component
public class ShowAllCoursesCommand implements Command {
	
	private final CourseService courseServices;
	
	@Autowired
	public ShowAllCoursesCommand(CourseService courseServices) {
		this.courseServices = courseServices;
	}

	@Override
	public void execute() {
		System.out.println("Here is the list of all courses that are available at the moment :");
		List<Course> courses = courseServices.getAllCourses();
		courses.stream()
				.forEach(course -> System.out.println(course.getCourseID() + ". "
						+ course.getCourseName() + ", "
						+ course.getCourseDescription()));
	}

	@Override
	public String getDescription() {
		return "show all avaible courses";

	}
}
