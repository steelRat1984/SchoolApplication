package ua.foxminded.SchoolApplication;

import ua.foxminded.SchoolApplication.controller.ConsoleMenu;
import ua.foxminded.SchoolApplication.dao.generation.DataGenerator;

public class Main {

	public static void main(String[] args) {
//		DataGenerator dataGenerator = new DataGenerator();
//		dataGenerator.generate();
		ConsoleMenu consoleMenu = new ConsoleMenu();
		consoleMenu.run();
	}
}
