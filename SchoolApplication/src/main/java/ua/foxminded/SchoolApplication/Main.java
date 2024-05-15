package ua.foxminded.SchoolApplication;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.SchoolApplication.config.SpringConfig;
import ua.foxminded.SchoolApplication.controller.ConsoleMenu;
import ua.foxminded.SchoolApplication.dao.CourseDAO;
import ua.foxminded.SchoolApplication.dao.StudentDAO;
import ua.foxminded.SchoolApplication.dao.generation.DataGenerator;
import ua.foxminded.SchoolApplication.model.Student;

public class Main {

	public static void main(String[] args) {
//		DataGenerator dataGenerator = new DataGenerator();
//		dataGenerator.generate();
//		ConsoleMenu consoleMenu = new ConsoleMenu();
//		consoleMenu.run();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		CourseDAO courseDAO = context.getBean("courseDao", CourseDAO.class);
		courseDAO.getCourseById(6);
	}
}
