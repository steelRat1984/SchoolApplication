package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.service.GroupService;
import ua.foxminded.SchoolApplication.service.StudentService;

@RequiredArgsConstructor
@Component
public class CreateStudentCommand implements Command {

	private final StudentService studentServices;
	private final Scanner scanner;
	private final GroupService groupService;

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
		boolean isCreated = studentServices.createStudent(student);
		if (isCreated == true) {
			String message = String.format("Student %s %s has been added", studentFirstname, studentLastname);
			System.out.println(message);
		} else {
			String message = String.format("Something wrong student %s %s has not been added, try again",
					studentFirstname, studentLastname);
			System.out.println(message);
			return;
		}
	}

	@Override
	public String getDescription() {
		return "create a new student";
	}
}
