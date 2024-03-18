package ua.foxminded.SchoolApplication;

import ua.foxminded.SchoolApplication.DAO.generation.DataGenerator;
import ua.foxminded.SchoolApplication.Service.GroupService;
import ua.foxminded.SchoolApplication.controller.ConsoleMenu;



public class Main {
	
	public static void main(String[] args) {
//		DataGenerator dataGenerator = new DataGenerator();
//		dataGenerator.generate();
//		ConsoleMenu consoleMenu = new ConsoleMenu();
//		consoleMenu.run();
		GroupService groupService = new GroupService();
		groupService.getAllGrouops();
	}
}
