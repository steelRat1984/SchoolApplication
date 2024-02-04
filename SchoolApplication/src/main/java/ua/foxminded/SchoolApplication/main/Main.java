package ua.foxminded.SchoolApplication.main;

import java.util.Scanner;
import ua.foxminded.SchoolApplication.schoolController.Controller;
public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
//		DatabaseLoader databaseLoader = new DatabaseLoader();
//		databaseLoader.load();
		Controller controller = new Controller(scanner);
		controller.run();
	}
}
