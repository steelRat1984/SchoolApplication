package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@Sql(scripts = "/create_schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class StudentDAOTest {
	 @Container
	    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
	            .withDatabaseName("test_school_app")
	            .withUsername("test_school_admin")
	            .withPassword("1234");
	
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    @Sql(scripts = "/insert_data.sql")
    public void setup () {
    	
    }
    @AfterEach
    @Sql(scripts = "/reset_tables.sql")
    public void tearDown() {
    	
    }


    @Test
    public void shouldCreateStudent() {
        Group group = new Group(1, "Group A");
        Student student = new Student(1, group, "John", "Doe");
        boolean isCreated = studentDAO.createStudent(student);
        assertTrue(isCreated);

        String sql = "SELECT COUNT(*) FROM school_app.students WHERE student_id = 1 AND first_name = 'John' AND last_name = 'Doe'";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(1, count);
    }

    @Test
//    public void shouldReturnAllStudents() {
//        Group group = new Group(1, "Group A");
//        Student student1 = new Student(1, group, "John", "Doe");
//        Student student2 = new Student(2, group, "Jane", "Smith");
//        String sql = "INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, student1.getGroup().getGroupID(), student1.getFirstName(), student1.getLastName());
//        jdbcTemplate.update(sql, student2.getGroup().getGroupID(), student2.getFirstName(), student2.getLastName());
//
//        List<Student> students = studentDAO.getAllStudents();
//
//        assertNotNull(students);
//        assertEquals(2, students.size());
//
//        Student foundStudent1 = students.get(0);
//        assertEquals("John", foundStudent1.getFirstName());
//        assertEquals("Doe", foundStudent1.getLastName());
//
//        Student foundStudent2 = students.get(1);
//        assertEquals("Jane", foundStudent2.getFirstName());
//        assertEquals("Smith", foundStudent2.getLastName());
//	придумати щось з іменами , якийсь сторонній конфігураційний файл звідки будуть ці класи брати цю строку 
//	навішати сюди тестконтейнер
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

