package ua.foxminded.SchoolApplication.dao.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

class StudentsResultSetExtractorTest {
	@Mock
	private ResultSet rs;

	@Mock
	private CourseMapper courseMapper;

	@Mock
	private StudentMapper studentMapper;

	@InjectMocks
	private StudentResultSetExtractor extractor;

	private Student student1;
	private Student student2;
	private Group groupA;
	private Group groupB;
	private Course courseMath;
	private Course courseSci;
	private Course courseHistory;
	private Course courseGeography;

	@BeforeEach
	public void setUp() throws SQLException {

		MockitoAnnotations.openMocks(this);

		groupA = new Group(1, "Group A");
		groupB = new Group(2, "Group B");
		courseMath = new Course(1, "Math", "Math course");
		courseSci = new Course(2, "Science", "Science course");
		courseGeography = new Course(3, "Geography", "Geography course");
		courseHistory = new Course(4, "History", "History course");
		student1 = new Student(1, groupA, "John", "Doe", new ArrayList<>());
		student2 = new Student(2, groupB, "Jack", "Smith", new ArrayList<>());

		when(rs.next()).thenReturn(true, true, true, true, true, true, false);
		when(rs.getInt("course_id")).thenReturn(1, 2, 3, 2, 3, 4);
		when(rs.getInt("student_id")).thenReturn(1, 1, 1, 2, 2, 2);
		when(rs.wasNull()).thenReturn(false);

		when(studentMapper.mapRow(any(ResultSet.class), anyInt()))
				.thenReturn(student1)
				.thenReturn(student2);

		when(courseMapper.mapRow(any(ResultSet.class), anyInt()))
				.thenReturn(courseMath)
				.thenReturn(courseSci)
				.thenReturn(courseGeography)
				.thenReturn(courseSci)
				.thenReturn(courseGeography)
				.thenReturn(courseHistory);
	}
	
	private static Optional<Course> getCourse(List<Course> courses, String courseName) {
		Optional<Course> result = courses.stream().filter(c -> c.getCourseName().equals(courseName)).findFirst();
		return result;
	}

	@Test
	public void shouldReturnStudents() throws SQLException {
		List<Student> actualStudents = extractor.extractData(rs);
		assertNotNull(actualStudents);
		assertEquals(2, actualStudents.size());

		Student actualStudent1 = actualStudents.get(0);
		assertNotNull(actualStudent1);
		assertEquals(1, actualStudent1.getStudentID());
		assertEquals("John", actualStudent1.getFirstName());
		assertEquals("Doe", actualStudent1.getLastName());
		assertEquals(3, actualStudent1.getCourses().size());

		Student actualStudent2 = actualStudents.get(1);
		assertNotNull(actualStudent2);
		assertEquals(2, actualStudent2.getStudentID());
		assertEquals("Jack", actualStudent2.getFirstName());
		assertEquals("Smith", actualStudent2.getLastName());
		assertEquals(3, actualStudent2.getCourses().size());

	}

	@Test
	public void shouldReturnCoursesForStudens() throws DataAccessException, SQLException {
		List<Student> actualStudents = extractor.extractData(rs);
		Student actualStudent2 = actualStudents.get(1);
		Student actualStudent1 = actualStudents.get(0);


		Course actualCourse1_1 = getCourse(actualStudent1.getCourses(), "Math").orElse(null);
		Course actualCourse1_2 = getCourse(actualStudent1.getCourses(), "Science").orElse(null);
		Course actualCourse1_3 = getCourse(actualStudent1.getCourses(), "Geography").orElse(null);
		Course actualCourse2_1 = getCourse(actualStudent2.getCourses(), "Science").orElse(null);
		Course actualCourse2_2 = getCourse(actualStudent2.getCourses(), "Geography").orElse(null);
		Course actualCourse2_3 = getCourse(actualStudent2.getCourses(), "History").orElse(null);

		assertEquals(1, actualCourse1_1.getCourseID());
		assertEquals("Math", actualCourse1_1.getCourseName());
		assertEquals("Math course", actualCourse1_1.getCourseDescription());

		assertEquals(2, actualCourse1_2.getCourseID());
		assertEquals("Science", actualCourse1_2.getCourseName());
		assertEquals("Science course", actualCourse1_2.getCourseDescription());

		assertEquals(3, actualCourse1_3.getCourseID());
		assertEquals("Geography", actualCourse1_3.getCourseName());
		assertEquals("Geography course", actualCourse1_3.getCourseDescription());

		assertEquals(2, actualCourse2_1.getCourseID());
		assertEquals("Science", actualCourse2_1.getCourseName());
		assertEquals("Science course", actualCourse2_1.getCourseDescription());

		assertEquals(3, actualCourse2_2.getCourseID());
		assertEquals("Geography", actualCourse2_2.getCourseName());
		assertEquals("Geography course", actualCourse2_2.getCourseDescription());

		assertEquals(4, actualCourse2_3.getCourseID());
		assertEquals("History", actualCourse2_3.getCourseName());
		assertEquals("History course", actualCourse2_3.getCourseDescription());
	}
}