package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;
import java.util.Scanner;


import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.service.CourseService;

@RequiredArgsConstructor
@Component
public class GetCourseReportCommand implements Command {
	
	private final CourseService courseService;
	private final Scanner scanner;


	@Override
	public void execute() {
		System.out.println("Please enter the course ID:");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		StringBuilder resultReport = new StringBuilder("Report on the names of students in courses :\n");
		Course course = courseService.getCourseById(courseId);
		List<Student> enrolledStudents = courseService.getStudentsOnCourse(courseId);
		resultReport.append("the following students study in the ").append(course.getCourseName()).append("course :\n");
		for (Student student : enrolledStudents) {
			String studentFullName = student.getFirstName() + " " + student.getLastName();
			resultReport.append(studentFullName).append("\n");
		}
		System.out.println(resultReport.toString());
	}

	@Override
	public String getDescription() {
		return "get a report on students on the course";
	}

}
