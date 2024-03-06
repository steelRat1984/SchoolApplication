package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.CourseService;
import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class EnrollStudentToCourseCommand implements Command {
	private final CourseService courseServices;
	private final StudentService studentServices;
	private final Scanner scanner;

	public EnrollStudentToCourseCommand(CourseService courseServices, StudentService studentServices, Scanner scanner) {
		this.courseServices = courseServices;
		this.studentServices = studentServices;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("Enter student's id :");
		int studentId = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter course id :");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		Student student = studentServices.getStudentById(studentId);
		Course course = courseServices.getCourseById(courseId);
		stringBuilder.append(student.getFirstName().trim()).append(" ").append(student.getLastName().trim());
		stringBuilder.append(" has been enrolled to the course -").append(course.getCourseName());
		studentServices.enrollStudentToCourse(studentId, courseId);
		System.out.println(stringBuilder.toString());

	}
	@Override
	public String getDescription() {
		String description = "enroll a student to the course";
		return description;
	}

}
