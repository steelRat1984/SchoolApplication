package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(scripts = "/create_schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

class StudentDAOTest {
    private static final Logger logger = LoggerFactory.getLogger(StudentDAOTest.class);

	
	 @Container
	    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
	            .withDatabaseName("school_app")
	            .withUsername("school_admin")
	            .withPassword("1234");
	
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    @Sql(scripts = "/insert_data.sql")
    public void setup() {
        System.out.println("Inserting test data");
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "school_app.students");
        System.out.println("Rows in students table before test: " + rowCount);
    }

    @AfterEach
    @Sql(scripts = "/reset_tables.sql")
    public void tearDown() {
        System.out.println("Resetting tables");
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "school_app.students");
        System.out.println("Rows in students table after reset: " + rowCount);
    }

    @Test
    public void shouldCreateStudent() {
        logger.info("Running shouldCreateStudent test");
        Group group = new Group(1, "Group A");
        Student student = new Student(1, group, "John", "Doe");
        boolean isCreated = studentDAO.createStudent(student);
        assertTrue(isCreated);

        String sql = "SELECT COUNT(*) FROM school_app.students WHERE student_id = 1 AND first_name = 'John' AND last_name = 'Doe'";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(1, count);
        logger.info("shouldCreateStudent test passed");
    }

    @Test
    public void shouldReturnAllStudents() {
        logger.info("Running shouldReturnAllStudents test");

        List<Student> students = studentDAO.getAllStudents();

        assertNotNull(students);
        assertEquals(3, students.size());  // Assuming there are 3 students in the database

        Student foundStudent1 = students.stream().filter(s -> s.getFirstName().equals("John") && s.getLastName().equals("Doe")).findFirst().orElse(null);
        assertNotNull(foundStudent1);

        Student foundStudent2 = students.stream().filter(s -> s.getFirstName().equals("Jane") && s.getLastName().equals("Smith")).findFirst().orElse(null);
        assertNotNull(foundStudent2);

        Student foundStudent3 = students.stream().filter(s -> s.getFirstName().equals("Emily") && s.getLastName().equals("Johnson")).findFirst().orElse(null);
        assertNotNull(foundStudent3);
        logger.info("shouldReturnAllStudents test passed");

    }
    
    }

