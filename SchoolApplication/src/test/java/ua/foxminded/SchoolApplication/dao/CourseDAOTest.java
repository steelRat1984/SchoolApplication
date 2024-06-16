package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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
import ua.foxminded.SchoolApplication.model.Student;

@Testcontainers
@JdbcTest
@ComponentScan(basePackages = "ua.foxminded.SchoolApplication")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(scripts = { "/create_schema.sql", "/create_tables.sql",
		"/insert_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/reset_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

class CourseDAOTest {

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3")
			.withDatabaseName("test")
			.withUsername("root")
			.withPassword("test");

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> "jdbc:tc:postgresql:16.3:///test?currentSchema=school_app&TC_REUSABLE=true");
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	public void shouldFindAllCoursesForStudent() {
		List<Course> courses = courseDAO.getSelectedCoursesForStudent(1);
		Course course1 = new Course(1, "Math", "Mathematics Course");
		Course course2 = new Course(2, "Science", "Science Course");
		
		assertEquals(2, courses.size());
		assertTrue(courses.stream().anyMatch(course -> course.equals(course1))); 
		assertTrue(courses.stream().anyMatch(course -> course.equals(course2))); 

	}
	
	@Test
	public void shoudRetyrnCourseById() {
		Course expectedCourse = new Course(1, "Math", "Mathematics Course");
		Course actualCourse = courseDAO.getCourseById(1);
		assertEquals(expectedCourse, actualCourse);
	}
	
	@Test
	public void shoudReturnAllCourses() {
		Course course1 = new Course(1, "Math", "Mathematics Course");
		Course course2 = new Course(2, "Science", "Science Course");
		List<Course> expectedCourses = Arrays.asList(course1, course2);
		List<Course> actualCourses = courseDAO.getAllCourses();
		assertEquals(expectedCourses.size(), actualCourses.size());
		assertEquals(expectedCourses, actualCourses);	
	}
	
	@Test
    public void shouldReturnAllStudentsOnCourse() {
        List<Student> students = courseDAO.getStudentsOnCourse(1);

        Student student1 = students.get(0);
        Student student2 = students.get(1);

        assertEquals(2, students.size());

        assertEquals("John", student1.getFirstName());
        assertEquals("Doe", student1.getLastName());

        assertEquals("Jane", student2.getFirstName());
        assertEquals("Smith", student2.getLastName());
	}	
	
	@Test
	public void shoudCreateCourse() {
		Course expectedCourse = new Course("Physic", "Physic course");
		courseDAO.createCourse(expectedCourse);
        String sql = "SELECT course_id FROM school_app.courses WHERE course_name = 'Physic' AND course_description = 'Physic course'";
		int courseId = jdbcTemplate.queryForObject(sql, Integer.class);
		expectedCourse.setCourseID(courseId);
		Course actualCourse = courseDAO.getCourseById(courseId);
		assertEquals(expectedCourse, actualCourse);
		
		
	
	}
}