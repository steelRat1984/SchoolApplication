package ua.foxminded.SchoolApplication.controller;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.controller.commands.CreateStudentCommand;
import ua.foxminded.SchoolApplication.controller.commands.DeleteStudentCommand;
import ua.foxminded.SchoolApplication.controller.commands.EnrollStudentToCourseCommand;
import ua.foxminded.SchoolApplication.controller.commands.GetCourseReportCommand;
import ua.foxminded.SchoolApplication.controller.commands.GetGroupReportCommand;
import ua.foxminded.SchoolApplication.controller.commands.RemoveStudentFromCourseCommand;
import ua.foxminded.SchoolApplication.controller.commands.ShowAllCoursesCommand;
import ua.foxminded.SchoolApplication.controller.commands.ShowStudentInfoByNameCommand;
import ua.foxminded.SchoolApplication.service.CourseService;
import ua.foxminded.SchoolApplication.service.GroupService;
import ua.foxminded.SchoolApplication.service.StudentService;

public class ConsoleMenu {
	private final CommandInvoker invoker = new CommandInvoker();
	private final Scanner scanner = new Scanner(System.in);
	private final StudentService studentService = new StudentService();
	private final CourseService courseService = new CourseService();
	private final GroupService groupService = new GroupService();

	public ConsoleMenu() {
		registerCommands();
	}

	private void registerCommands() {
		invoker.register(1, new ShowAllCoursesCommand(courseService));
		invoker.register(2, new GetGroupReportCommand(groupService));
		invoker.register(3, new GetCourseReportCommand(courseService, scanner));
		invoker.register(4, new CreateStudentCommand(studentService, scanner, groupService));
		invoker.register(5, new ShowStudentInfoByNameCommand(studentService, scanner));
		invoker.register(6, new DeleteStudentCommand(studentService, scanner));
		invoker.register(7, new EnrollStudentToCourseCommand(courseService, studentService, scanner));
		invoker.register(8, new RemoveStudentFromCourseCommand(courseService, studentService, scanner));

	}

	public void run() {
		invoker.showCommandsDescription();
		try {
			while (true) {
				int commandId = Integer.parseInt(scanner.nextLine());
				if (commandId == 0) {
					System.out.println("Exiting...");
					break;
				}
				invoker.executeCommand(commandId);
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a correct command identifier.");
		} finally {
			scanner.close();
		}
	}
}
