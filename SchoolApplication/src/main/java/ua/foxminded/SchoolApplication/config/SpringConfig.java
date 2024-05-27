package ua.foxminded.SchoolApplication.config;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ua.foxminded.SchoolApplication")
public class SpringConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
    
}
