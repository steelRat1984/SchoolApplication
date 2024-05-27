package ua.foxminded.SchoolApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ua.foxminded.SchoolApplication.controller.ConsoleMenu;

@SpringBootApplication
public class SchoolApplication {
	   public static void main(String[] args) {
	        ApplicationContext context = SpringApplication.run(SchoolApplication.class, args);
	        ConsoleMenu consoleMenu = context.getBean(ConsoleMenu.class);
	        consoleMenu.run();
	    }	
}