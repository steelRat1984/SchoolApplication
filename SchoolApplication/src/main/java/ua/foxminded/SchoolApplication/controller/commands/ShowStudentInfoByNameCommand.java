package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Student;

public class ShowStudentInfoByNameCommand implements Command {
	private final StudentService studentServices;
	private final Scanner scanner;
	
	public ShowStudentInfoByNameCommand(StudentService services, Scanner scanner) {
		this.studentServices = services;
		this.scanner = scanner;
	}

	@Override
	public void execute() {
		System.out.println("Enter student's first name :");
		String studentFirstName = scanner.nextLine();
		System.out.println("Enter student's last name ");
		String studentLastName = scanner.nextLine();
		Student student = studentServices.getStudentnByName(studentFirstName, studentLastName);
		System.out.println("infirmation about student :");
		System.out.println(student);
	}

	@Override
	public String getDescription() {
		String description = "show student info";
		return description;
	}

}
