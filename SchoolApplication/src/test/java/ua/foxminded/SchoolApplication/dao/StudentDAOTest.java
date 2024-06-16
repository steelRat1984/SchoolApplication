package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.SchoolApplication.model.Course;
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
    public void shoudReturnStudentByName() {
    	String firstName = "John";
    	String lastName = "Doe";
    	Student student = studentDAO.getStudentByName(firstName, lastName);
    	assertEquals(firstName, student.getFirstName());
    	assertEquals(lastName, student.getLastName());
    }

    @Test
    public void shouldReturnAllStudents() {
        List<Student> students = studentDAO.getAllStudents();

        assertNotNull(students);
        assertEquals(3, students.size());

        Student exspectedStudent1 = studentDAO.getStudentByName("John", "Doe");
        Student exspectedStudent2 = studentDAO.getStudentByName("Jane", "Smith");
        Student exspectedStudent3 = studentDAO.getStudentByName("Emily", "Johnson");
        
        assertNotNull(exspectedStudent1);
        assertNotNull(exspectedStudent2);
        assertNotNull(exspectedStudent3);        

    }
    
    @Test
    public void shouldReturnStudentById() {
        Student actualStudent1 = studentDAO.getStudentByName("John", "Doe");

        Student expectedStudent1 = studentDAO.getStudentById(actualStudent1.getStudentID());

        assertEquals(expectedStudent1.getStudentID(), actualStudent1.getStudentID());
    }
    
    @Test
    public void shouldDeleteStudentByID() {
        Student actualStudent1 = studentDAO.getStudentByName("John", "Doe");
;
        studentDAO.deleteStudentById(actualStudent1.getStudentID());

        assertNull(studentDAO.getStudentById(actualStudent1.getStudentID()));

    }
    
    @Test
    public void shoudDeleteAssigment () {
        int studentId = jdbcTemplate.queryForObject("SELECT student_id FROM school_app.students WHERE first_name = 'John' AND last_name = 'Doe'", Integer.class);
        int courseId = jdbcTemplate.queryForObject("SELECT course_id FROM school_app.courses WHERE course_name = 'Math'", Integer.class);
        boolean deleted = studentDAO.deleteAssignments(studentId, courseId);
        assertTrue(deleted);
        
        String sql = "SELECT COUNT(*) FROM school_app.students_courses WHERE student_id = (SELECT student_id FROM school_app.students WHERE first_name = 'John' AND last_name = 'Doe') AND course_id = (SELECT course_id FROM school_app.courses WHERE course_name = 'Math')";
        int counter = jdbcTemplate.queryForObject(sql, Integer.class);
        assertFalse(counter > 0);
        
    }
    
    @Test
    public void shoudDeleteAllAssigmentsByStudentId () {
    	String sql = "SELECT student_id FROM school_app.students WHERE first_name = 'John' AND last_name = 'Doe'";
    	int studentId = jdbcTemplate.queryForObject(sql, Integer.class);
    	studentDAO.deleteAllAssignmentsByStudentId(studentId);
    	String sql2 = "SELECT COUNT(*) FROM school_app.students_courses WHERE student_id = " + studentId; 
    	int nunberAssigments = jdbcTemplate.queryForObject(sql2, Integer.class); 
    	assertTrue(nunberAssigments == 0);
    }
    
    @Test
    public void shoudCreateAssigment () {
    	Course physic = new Course(11, "physic", "learning physic");
    	Course geography = new Course(12, "geography", "learning geography");
    	List<Course> courses = new ArrayList<Course>();
    	courses.add(physic);
    	courses.add(geography);
    	Student student = new Student(10, new Group(), "Vlad", "Slyvych", courses);
    	studentDAO.createAssignment(student);
    	String sqlSelectIdPhysic = "SELECT student_id FROM school_app.students_courses WHERE course_id = " + physic.getCourseID(); 
    	String sqlSelectIdgeography = "SELECT student_id FROM school_app.students_courses WHERE course_id = " + geography.getCourseID(); 
    	String sqlSelectNumber = "SELECT COUNT(*) FROM school_app.students_courses WHERE student_id = " + student.getStudentID(); 
    	int actualStudentIdForPhysic = jdbcTemplate.queryForObject(sqlSelectIdPhysic, Integer.class);
    	int actualStudentIdForGeography = jdbcTemplate.queryForObject(sqlSelectIdgeography, Integer.class);
    	int actualStringNumber = jdbcTemplate.queryForObject(sqlSelectNumber, Integer.class);
    	assertEquals(10, actualStudentIdForPhysic);
    	assertEquals(10, actualStudentIdForGeography);
    	assertEquals(2, actualStringNumber);
    	
    }
    
    }

