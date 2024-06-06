package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

@Testcontainers
@JdbcTest
@ComponentScan(basePackages = "ua.foxminded.SchoolApplication")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(scripts = {"/create_schema.sql", "/create_tables.sql", "/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/reset_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

class StudentDAOTest {
	
	 @Container
	    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3")
	            .withDatabaseName("test")
	            .withUsername("root")
	            .withPassword("test");
	
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:16.3:///test?currentSchema=school_app&TC_REUSABLE=true");
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    
    @BeforeEach
    public void setup() {
        System.out.println("Inserting test data");
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "school_app.students");
        System.out.println("Rows in students table before test: " + rowCount);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Resetting tables");
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "school_app.students");
        System.out.println("Rows in students table after reset: " + rowCount);
    }

    @Test
    public void shouldCreateStudent() {
        Group group = new Group(1, "Group A");
        Student student = new Student(1, group, "Petro", "Doe");
        boolean isCreated = studentDAO.createStudent(student);
        assertTrue(isCreated);

        String sql = "SELECT COUNT(*) FROM school_app.students WHERE first_name = 'Petro' AND last_name = 'Doe'";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(1, count);
    }

    @Test
    public void shouldReturnAllStudents() {

        List<Student> students = studentDAO.getAllStudents();

        assertNotNull(students);
        assertEquals(3, students.size());  // Assuming there are 3 students in the database

        Student foundStudent1 = students.stream().filter(s -> s.getFirstName().equals("John") && s.getLastName().equals("Doe")).findFirst().orElse(null);
        assertNotNull(foundStudent1);

        Student foundStudent2 = students.stream().filter(s -> s.getFirstName().equals("Jane") && s.getLastName().equals("Smith")).findFirst().orElse(null);
        assertNotNull(foundStudent2);

        Student foundStudent3 = students.stream().filter(s -> s.getFirstName().equals("Emily") && s.getLastName().equals("Johnson")).findFirst().orElse(null);
        assertNotNull(foundStudent3);
    }
    
    }

