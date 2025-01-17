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
public class RemoveStudentFromCourseCommand implements Command {
	
	private final CourseService courseServices;
	private final StudentService studentServices;
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
		Student student = studentServices.getStudentById(studentId);
		Course course = courseServices.getCourseById(courseId);
		boolean isDeleted = studentServices.deletedStudentFromCourse(studentId, courseId);
		if (isDeleted == true) {
			stringBuilder.append(student.getFirstName().trim()).append(" ").append(student.getLastName().trim());
			stringBuilder.append(" has been deleted from the course -").append(course.getCourseName());
			System.out.println(stringBuilder.toString());
		} else {
			stringBuilder.append(student.getFirstName()).append(" ").append(student.getLastName());
			stringBuilder.append(" did not study in this course - ").append(course.getCourseName()).append(", please choose another one");
			System.out.println(stringBuilder.toString());
		}
		
		
	}

	@Override
	public String getDescription() {
		return "remove the student from the course"; 
	}
}
