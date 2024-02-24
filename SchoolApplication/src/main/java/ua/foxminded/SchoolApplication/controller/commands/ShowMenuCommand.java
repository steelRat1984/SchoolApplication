package ua.foxminded.SchoolApplication.controller.commands;

import ua.foxminded.SchoolApplication.controller.Command;

public class ShowMenuCommand implements Command{

	@Override
	public void execute() {
		StringBuilder menuCommands = new StringBuilder();

		String message = "choise an option: \n";
		String option1 = "1. show all options\n";
		String option2 = "2. show actual course list:\n";
		String option3 = "3. get report about number students in groups :\n";
		String option4 = "4. get list of students at the course\n";
		String option5 = "5. create a new student \n";
		String option6 = "6. show information about student by name \n";
		String option7 = "7. delete a student \n";
		String option8 = "8. enroll student at the course \n";
		String option9 = "9. remove student at the course\n";
		String optionExit = "0. EXIT";
		menuCommands.append(message);
		menuCommands.append(option1);
		menuCommands.append(option2);
		menuCommands.append(option3);
		menuCommands.append(option4);
		menuCommands.append(option5);
		menuCommands.append(option6);
		menuCommands.append(option7);
		menuCommands.append(option8);
		menuCommands.append(option9);
		menuCommands.append(optionExit);
		System.out.println(menuCommands.toString());

	}

}
