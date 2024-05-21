package ua.foxminded.SchoolApplication.config;

import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ua.foxminded.SchoolApplication")
@PropertySource("classpath:database.properties")
public class SpringConfig {
	
	private final Environment environment;
	private final ApplicationContext applicationContext;
	
@Autowired
	public SpringConfig(Environment environment, ApplicationContext applicationContext) {
		this.environment = environment;
		this.applicationContext = applicationContext;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/school_app");
		dataSource.setUsername("school_admin");
		dataSource.setPassword("1234");

		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public Scanner scanner () {
		return new Scanner(System.in);
	}
}
