package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ua.foxminded.SchoolApplication.Services.CourseServices;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class GetCourseReportCommand implements Command {
	private final CourseServices courseServices;
	private final Scanner scanner;

	public GetCourseReportCommand(CourseServices courseServices, Scanner scanner) {
		this.courseServices = courseServices;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		System.out.println("Please enter the course ID:");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		StringBuilder resultReport = new StringBuilder("Report on the names of students in courses:\n");
		Map<Course, List<Student>> courseReport = courseServices.getDataForCertainCourseReport(courseId);
		for (Map.Entry<Course, List<Student>> entry : courseReport.entrySet()) {
			String courseName = entry.getKey().getCourseName();
			List<Student> students = entry.getValue();
			
				resultReport.append("the following students study in the ").append(courseName).append("course");
				for (Student student : students){
					String studentFullName = student.getFirstName().trim()+ " " + student.getLastName().trim();
					resultReport.append(studentFullName).append("\n");
				}
		}
		System.out.println(resultReport.toString());

	}

}
