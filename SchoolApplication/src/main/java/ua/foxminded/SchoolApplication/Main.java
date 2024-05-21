package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.SchoolApplication.config.SpringConfig;
import ua.foxminded.SchoolApplication.controller.ConsoleMenu;


public class Main {

	public static void main(String[] args) {
////		DataGenerator dataGenerator = new DataGenerator();
////		dataGenerator.generate();
		
		try (AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class)) {
			ConsoleMenu consoleMenu = annotationConfigApplicationContext.getBean(ConsoleMenu.class);
			consoleMenu.run();
			
		} catch (BeansException e) {
			e.printStackTrace();
		}
		
		
				}
	

	    }

