package ua.foxminded.SchoolApplication.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.dao.generation.DataGenerator;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private static DatabaseChecker databaseChecker;
	private static DataGenerator generator;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (databaseChecker.isDatabaseEmpty()) {
			generator.generate();
		}
	}
}
