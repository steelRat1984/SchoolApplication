package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.CourseService;
import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class EnrollStudentToCourseCommand implements Command {
	private final CourseService courseService;
	private final StudentService studentService;
	private final Scanner scanner;

	public EnrollStudentToCourseCommand(CourseService courseService, StudentService studentService, Scanner scanner) {
		this.courseService = courseService;
		this.studentService = studentService;
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
		Student student = studentService.getStudentById(studentId);
		Course course = courseService.getCourseById(courseId);
		boolean isEnrolled = studentService.enrollStudentToCourse(studentId, courseId);
		if (isEnrolled == true) {
			stringBuilder.append(student.getFirstName().trim()).append(" ").append(student.getLastName().trim());
			stringBuilder.append(" has been enrolled to the course - ").append(course.getCourseName());
			System.out.println(stringBuilder.toString());
		} else {
			stringBuilder.append(student.getFirstName()).append(" ").append(student.getLastName());
			stringBuilder.append(" is already enrolled in this course - ").append(course.getCourseName()).append(", please choose another one");
			System.out.println(stringBuilder.toString());
		}
		

	}
	@Override
	public String getDescription() {
		return "enroll a student to the course";
		
	}

}
