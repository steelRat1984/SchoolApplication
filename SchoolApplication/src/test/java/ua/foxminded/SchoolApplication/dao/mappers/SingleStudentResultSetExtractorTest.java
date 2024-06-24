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

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);

		when(rs.next()).thenReturn(true, true, false);
		when(rs.getInt("student_id")).thenReturn(1);
		when(rs.getString("first_name")).thenReturn("John");
		when(rs.getString("last_name")).thenReturn("Doe");
		when(rs.getInt("group_id")).thenReturn(1);
		when(rs.getString("group_name")).thenReturn("Group A");
		when(rs.getInt("course_id")).thenReturn(1, 2);
		when(rs.getString("course_name")).thenReturn("Math", "Science");
		when(rs.getString("course_description")).thenReturn("Math course", "Science course");
		when(studentMapper.mapRow(any(ResultSet.class), anyInt())).thenAnswer(invocation -> {
			ResultSet rsMock = invocation.getArgument(0);
			Group group = new Group(rsMock.getInt("group_id"), rsMock.getString("group_name").trim());
			return new Student(rsMock.getInt("student_id"), group, rsMock.getString("first_name").trim(),
					rsMock.getString("last_name").trim(), new ArrayList<>());
		});
		when(courseMapper.mapRow(any(ResultSet.class), anyInt())).thenAnswer(invocation -> {
			ResultSet rsMock = invocation.getArgument(0);
			int courseId = rsMock.getInt("course_id");
			String courseName = rsMock.getString("course_name").trim();
			String courseDescription = rsMock.getString("course_description").trim();
			return new Course(courseId, courseName, courseDescription);
		});

	}

	@Test
	public void shoudNotReturnNullStudentAndCourses() throws DataAccessException, SQLException {
		Student actualStudent = extractor.extractData(rs);
		assertNotNull(actualStudent);
		assertNotNull(actualStudent.getCourses().get(0));
		assertNotNull(actualStudent.getCourses().get(1));
	}

	@Test
	public void shouldReturnProperStudentFields() throws DataAccessException, SQLException {
		Student actualStudent = extractor.extractData(rs);
		assertEquals(1, actualStudent.getStudentID());
		assertEquals("John", actualStudent.getFirstName());
		assertEquals("Doe", actualStudent.getLastName());

	}

	@Test
	public void shoudReturnProperCourseFields() throws DataAccessException, SQLException {
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
