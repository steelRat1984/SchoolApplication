package ua.foxminded.SchoolApplication.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import ua.foxminded.SchoolApplication.dao.generation.Generator;

public class DataInitializer implements ApplicationRunner {

	private static DatabaseChecker databaseChecker;
	private static Generator generator;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (databaseChecker.isDatabaseEmpty()) {
			generator.generate();
		}
	}

}
