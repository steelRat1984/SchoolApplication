package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Services.StudentServices;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Student;

public class DeleteStudentCommand implements Command {
	private final  StudentServices studentServices;
	private final Scanner scanner;

	public DeleteStudentCommand(StudentServices studentServices, Scanner scanner) {
		this.studentServices = studentServices;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		System.out.println("Enter student's Id: ");
		int studentId = scanner.nextInt();
		Student student = studentServices.getStudentById(studentId);
		studentServices.deleteStudentById(studentId);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Student \n");
		stringBuilder.append(student.toString() + "\n");
		stringBuilder.append("has been deleted!");
		System.out.println(stringBuilder.toString());

	}

}
