package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Student;

public class CreateStudentCommand implements Command {
	private final StudentService studentServices;
	private final Scanner scanner;

	public CreateStudentCommand(StudentService studentServices, Scanner scanner) {
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
		studentServices.createStudent(student);
		String message = String.format("Student %s %s has been added", studentFirstname, studentLastname);
		System.out.println(message);
	}

	@Override
	public String getDescription() {
		return "create a new student";
	}
}
