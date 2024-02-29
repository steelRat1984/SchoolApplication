package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Services.StudentServices;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Student;

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
		Student student = new Student();
		student.setFirstName(studentFirstname);
		student.setLastName(studentLastname);
		studentServices.insertStudent(student);
		String message = String.format("Student %s %s has been added", studentFirstname, studentLastname);
		System.out.println(message);
	}
}
