package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.CourseService;
import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class GetCourseReportCommand implements Command {
	private final CourseService courseServices;
	private final StudentService studentService;
	private final Scanner scanner;

	public GetCourseReportCommand(CourseService courseServices, StudentService studentService, Scanner scanner) {
		this.courseServices = courseServices;
		this.studentService = studentService;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		System.out.println("Please enter the course ID:");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		StringBuilder resultReport = new StringBuilder("Report on the names of students in courses :\n");
		Course course = courseServices.getCourseById(courseId);
		List<Student> enrolledStudents = studentService.getDataForCertainCourseReport(courseId);
		resultReport.append("the following students study in the ").append(course.getCourseName()).append("course :\n");
		for (Student student : enrolledStudents) {
			String studentFullName = student.getFirstName() + " " + student.getLastName();
			resultReport.append(studentFullName).append("\n");
		}
		System.out.println(resultReport.toString());
	}

	@Override
	public String getDescription() {
		String description = "get a report on students on the course";
		return description;
	}

}
