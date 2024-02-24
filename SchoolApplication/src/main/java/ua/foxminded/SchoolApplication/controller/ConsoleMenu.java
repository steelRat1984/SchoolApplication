package ua.foxminded.SchoolApplication.controller;

import java.util.Scanner;

import ua.foxminded.SchoolApplication.Services.CourseServices;
import ua.foxminded.SchoolApplication.Services.GroupServices;
import ua.foxminded.SchoolApplication.Services.StudentServices;
import ua.foxminded.SchoolApplication.controller.commands.CreateStudentCommand;
import ua.foxminded.SchoolApplication.controller.commands.DeleteStudentCommand;
import ua.foxminded.SchoolApplication.controller.commands.EnrollStudentToCourseCommand;
import ua.foxminded.SchoolApplication.controller.commands.GetCourseStatisticReportCommand;
import ua.foxminded.SchoolApplication.controller.commands.GetGroupStatisticReportCommand;
import ua.foxminded.SchoolApplication.controller.commands.RemoveStudentFromCourseCommand;
import ua.foxminded.SchoolApplication.controller.commands.ShowAllCoursesCommand;
import ua.foxminded.SchoolApplication.controller.commands.ShowMenuCommand;
import ua.foxminded.SchoolApplication.controller.commands.ShowStudentInfoByNameCommand;

public class ConsoleMenu {
	private final CommandInvoker invoker = new CommandInvoker();
	private final Scanner scanner = new Scanner(System.in);
	private final StudentServices studentServices = new StudentServices();
	private final CourseServices courseServices = new CourseServices();
	private final GroupServices groupServices = new GroupServices();

	public ConsoleMenu() {
		registerCommands();
	}

	private void registerCommands() {
		invoker.register(1, new ShowMenuCommand());
		invoker.register(2, new ShowAllCoursesCommand(courseServices));
		invoker.register(3, new GetGroupStatisticReportCommand(groupServices));
		invoker.register(4, new GetCourseStatisticReportCommand(courseServices, scanner));
		invoker.register(5, new CreateStudentCommand(studentServices, scanner));
		invoker.register(6, new ShowStudentInfoByNameCommand(studentServices, scanner));
		invoker.register(7, new DeleteStudentCommand(studentServices, scanner));
		invoker.register(8, new EnrollStudentToCourseCommand(courseServices, studentServices, scanner));
		invoker.register(9, new RemoveStudentFromCourseCommand(courseServices, studentServices, scanner));

	}

	public void run() {
		invoker.executeCommand(1);
		try {
			while (true) {
				int commandId = Integer.parseInt(scanner.nextLine());
				if (commandId == 0) {
					System.out.println("Exiting...");
					break;
				}
				invoker.executeCommand(commandId);
				System.out.println("\n"
						+ "To see all available options, press 1");
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a correct command identifier.");
		} finally {
			scanner.close();
		}
	}
}
