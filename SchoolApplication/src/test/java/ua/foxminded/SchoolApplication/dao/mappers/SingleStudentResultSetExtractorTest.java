package ua.foxminded.SchoolApplication.dao.mappers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class SingleStudentResultSetExtractorTest {

	@Mock
	private ResultSet rs;

	@Mock
	private CourseMapper courseMapper;

	@Mock
	private StudentMapper studentMapper;

	@InjectMocks
	private SingleStudentResultSetExtractor extractor;
	
	private Student student;
    private Course course1;
    private Course course2;

	  @BeforeEach
	    public void setUp() throws SQLException {
	        MockitoAnnotations.openMocks(this);
	        
	        Group group = new Group(1, "Group A");
	        student = new Student(1, group, "John", "Doe", new ArrayList<>());
	        course1 = new Course(1, "Math", "Math course");
	        course2 = new Course(2, "Science", "Science course");

	        when(studentMapper.mapRow(any(ResultSet.class), anyInt())).thenReturn(student);
	        when(courseMapper.mapRow(any(ResultSet.class), anyInt())).thenReturn(course1).thenReturn(course2);

	        when(rs.next()).thenReturn(true, true, false); 
	        when(rs.getInt("course_id")).thenReturn(1).thenReturn(2);
	        when(rs.wasNull()).thenReturn(false);
	    }

	@Test
	public void shoudNotReturnNullStudentAndCourses() throws DataAccessException, SQLException {
		Student actualStudent = extractor.extractData(rs);
		assertNotNull(actualStudent);
		assertNotNull(actualStudent.getCourses().get(0));
		assertNotNull(actualStudent.getCourses().get(1));
	}

	@Test
	public void shouldReturnCorrectStudentFields() throws DataAccessException, SQLException {
		Student actualStudent = extractor.extractData(rs);
		assertEquals(1, actualStudent.getStudentID());
		assertEquals("John", actualStudent.getFirstName());
		assertEquals("Doe", actualStudent.getLastName());

	}

	@Test
	public void shoudReturnCorrectCourseFields() throws DataAccessException, SQLException {
		Student actualStudent = extractor.extractData(rs);
		Course actualCourse1 = actualStudent.getCourses().stream()
				.filter(course -> course.getCourseName() == "Math")
				.findFirst()
				.orElseThrow(() -> new AssertionError("Course with name Math not found"));
		Course actualCourse2 = actualStudent.getCourses().stream()
				.filter(course -> course.getCourseName() == "Science")
				.findFirst()
				.orElseThrow(() -> new AssertionError("Course with name Science not found"));

		assertEquals(2, actualStudent.getCourses().size());
		assertEquals("Math", actualCourse1.getCourseName());
		assertEquals("Math course", actualCourse1.getCourseDescription());
		assertEquals("Science", actualCourse2.getCourseName());
		assertEquals("Science course", actualCourse2.getCourseDescription());
	}
}
