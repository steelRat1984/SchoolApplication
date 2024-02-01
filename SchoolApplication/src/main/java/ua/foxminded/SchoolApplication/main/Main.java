package ua.foxminded.SchoolApplication.main;

import java.util.Scanner;
import ua.foxminded.SchoolApplication.database.DatabaseLoader;
import ua.foxminded.SchoolApplication.model.CourseName;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.schoolController.Controller;
import ua.foxminded.SchoolApplication.schoolController.StudentManager;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
//		DatabaseLoader databaseLoader = new DatabaseLoader();
//		databaseLoader.load();
		Controller controller = new Controller(scanner);
		controller.run();

		
	}

}
