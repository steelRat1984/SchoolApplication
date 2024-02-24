package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Services.StudentServices;
import ua.foxminded.SchoolApplication.controller.Command;

public class CreateStudentCommand implements Command {
	private StudentServices studentServices = new StudentServices();
	Scanner scanner = new Scanner(System.in);

	public CreateStudentCommand(StudentServices studentServices, Scanner scanner) {
		this.studentServices = studentServices;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		System.out.println("Enter student's first name:");
		String studentFirstname = scanner.nextLine();

		System.out.println("Enter student's last name:");
		String studentLastname = scanner.nextLine();
		
		studentServices.insertStudent(studentFirstname, studentLastname);
		StringBuilder stringBuilder = new StringBuilder("Student ");
		stringBuilder.append(studentFirstname).append(" ").append(studentLastname);
		stringBuilder.append(" has been added!");
		System.out.println(stringBuilder.toString());
	}
}
