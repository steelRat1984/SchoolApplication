package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Service.GroupService;
import ua.foxminded.SchoolApplication.Service.StudentService;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class CreateStudentCommand implements Command {
	private final StudentService studentServices;
	private final Scanner scanner;
	private final GroupService groupService;

	public CreateStudentCommand(StudentService studentServices, Scanner scanner, GroupService groupService) {
		super();
		this.studentServices = studentServices;
		this.scanner = scanner;
		this.groupService = groupService;
	}

	@Override
	public void execute() {
		System.out.println("Enter student's first name:");
		String studentFirstname = scanner.nextLine();
		System.out.println("Enter student's last name:");
		String studentLastname = scanner.nextLine();
		System.out.println("Enter number of the group:");
		int groupId = scanner.nextInt();
		scanner.nextLine();
		Group group = groupService.getGroupById(groupId);
		Student student = new Student();
		student.setFirstName(studentFirstname);
		student.setLastName(studentLastname);
		student.setGroup(group);
		studentServices.createStudent(student);
		String message = String.format("Student %s %s has been added", studentFirstname, studentLastname);
		System.out.println(message);
	}

	@Override
	public String getDescription() {
		return "create a new student";
	}
}
