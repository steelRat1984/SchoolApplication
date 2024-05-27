package ua.foxminded.SchoolApplication.controller.commands;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.service.StudentService;

@RequiredArgsConstructor
@Component
public class DeleteStudentCommand implements Command {

	private final StudentService studentServices;
	private final Scanner scanner;

	@Override
	public void execute() {
		System.out.println("Enter student's Id: ");
		int studentId = scanner.nextInt();
		scanner.nextLine();
		Student student = studentServices.getStudentById(studentId);
		studentServices.deleteStudentById(studentId);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Student \n");
		stringBuilder.append(student.getFirstName() + " " + student.getLastName() + "\n");
		stringBuilder.append("has been deleted!");
		System.out.println(stringBuilder.toString());

	}

	@Override
	public String getDescription() {
		return "delete the student";
	}

}
