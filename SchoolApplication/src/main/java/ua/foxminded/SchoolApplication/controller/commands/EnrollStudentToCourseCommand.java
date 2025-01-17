package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.service.CourseService;
import ua.foxminded.SchoolApplication.service.StudentService;

@RequiredArgsConstructor
@Component
public class EnrollStudentToCourseCommand implements Command {

	private final CourseService courseService;
	private final StudentService studentService;
	private final Scanner scanner;

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
		if (student == null) {
			System.out.println("Student with ID " + studentId + " not found.");
			return;
		}
		Course course = courseService.getCourseById(courseId);
		boolean isEnrolled = studentService.enrollStudentToCourse(student, course);
		if (isEnrolled == true) {
			stringBuilder.append("success! ");
			stringBuilder.append(student.getFirstName()).append(" ").append(student.getLastName());
			stringBuilder.append(" was enrolled in this course - ").append(course.getCourseName());
			System.out.println(stringBuilder.toString());
		} else {
			stringBuilder.append(student.getFirstName()).append(" ").append(student.getLastName());
			stringBuilder.append(" is already enrolled in this course - ").append(course.getCourseName())
					.append(", please choose another one");
			System.out.println(stringBuilder.toString());
		}
	}

	@Override
	public String getDescription() {
		return "enroll a student to the course";

	}

}
