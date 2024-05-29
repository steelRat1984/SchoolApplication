package ua.foxminded.SchoolApplication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Transactional
class StudentDAOTest {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeAll
    static void setUpOnce(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DROP SCHEMA IF EXISTS school_app CASCADE");
        jdbcTemplate.execute("CREATE SCHEMA school_app");

        jdbcTemplate.execute("CREATE TABLE school_app.groups (group_id INT PRIMARY KEY, group_name VARCHAR(255));");
        jdbcTemplate.execute("CREATE TABLE school_app.courses (course_id INT PRIMARY KEY, course_name VARCHAR(255), course_description TEXT);");

        jdbcTemplate.update("INSERT INTO school_app.groups (group_id, group_name) VALUES (1, 'Group A');");
        jdbcTemplate.update("INSERT INTO school_app.courses (course_id, course_name, course_description) VALUES (1, 'Course A', 'Description A');");
    }

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS school_app.students_courses");
        jdbcTemplate.execute("DROP TABLE IF EXISTS school_app.students");

        jdbcTemplate.execute("CREATE TABLE school_app.students (student_id SERIAL PRIMARY KEY, group_id INT, first_name VARCHAR(255), last_name VARCHAR(255), FOREIGN KEY (group_id) REFERENCES school_app.groups (group_id));");
        jdbcTemplate.execute("CREATE TABLE school_app.students_courses (student_id INT, course_id INT, PRIMARY KEY (student_id, course_id), FOREIGN KEY (student_id) REFERENCES school_app.students (student_id), FOREIGN KEY (course_id) REFERENCES school_app.courses (course_id));");
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
    public void shouldReturnAllStudents() {

        Group group = new Group(1, "Group A");
        Student student1 = new Student(1, group, "John", "Doe");
        Student student2 = new Student(2, group, "Jane", "Smith");
        String sql = "INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, student1.getGroup().getGroupID(),
        		student1.getFirstName(), student1.getLastName());
        jdbcTemplate.update(sql, student2.getGroup().getGroupID(),
                student2.getFirstName(), student2.getLastName());

        List<Student> students = studentDAO.getAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());

        Student foundStudent1 = students.get(0);
        assertEquals("John", foundStudent1.getFirstName());
        assertEquals("Doe", foundStudent1.getLastName());

        Student foundStudent2 = students.get(1);
        assertEquals("Jane", foundStudent2.getFirstName());
        assertEquals("Smith", foundStudent2.getLastName());
    }

    
    
    
    
    
    
}
