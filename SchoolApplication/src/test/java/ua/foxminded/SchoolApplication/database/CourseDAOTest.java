package ua.foxminded.SchoolApplication.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.model.Course;

class CourseDAOTest {

	@Mock
	private Connection connectionMock;

	@Mock
	private PreparedStatement preparedStatementMock;

	@Mock
	private ResultSet resultSetMock;

	private CourseDAO courseDAO;
	private MockedStatic<Database> databaseMock;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		databaseMock = Mockito.mockStatic(Database.class);
	 	databaseMock.when(Database::connection).thenReturn(connectionMock);	
		
		when (connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when (preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		courseDAO = new CourseDAO();
	}

	@AfterEach
	public void tearDown() {
		databaseMock.close();
	}

	@Test
	public void testGetCourseByID() throws SQLException {
		String testName = "TestName";
		String testDescriprion = "TestDescription";
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getInt("course_id")).thenReturn(1);
		when(resultSetMock.getString("course_name")).thenReturn(testName);
		when(resultSetMock.getString("course_description")).thenReturn(testDescriprion);
		Course resultCourse = courseDAO.getCourseById(1);
		assertEquals(1, resultCourse.getCourseID());
		assertEquals(testName, resultCourse.getCourseName());
		assertEquals(testDescriprion, resultCourse.getCourseDescription());
	}

	@Test
	public void testGetListActualCoursesList() throws SQLException {
		String name1 = "Name1";
		String name2 = "Name2";
		String description1 = "Descriprion1";
		String description2 = "Descriprion2";

		when(resultSetMock.next()).thenReturn(true, true, false);
		when(resultSetMock.getInt("course_id")).thenReturn(1, 2);
		when(resultSetMock.getString("course_name")).thenReturn(name1, name2);
		when(resultSetMock.getString("course_description")).thenReturn(description1, description2);
		List<Course> resultCoursesList = courseDAO.getAllCourses();

		assertEquals(2, resultCoursesList.size());
		assertEquals(1, resultCoursesList.get(0).getCourseID());
		assertEquals(2, resultCoursesList.get(1).getCourseID());
		assertEquals(name1, resultCoursesList.get(0).getCourseName());
		assertEquals(name2, resultCoursesList.get(1).getCourseName());
		assertEquals(description1, resultCoursesList.get(0).getCourseDescription());
		assertEquals(description2, resultCoursesList.get(1).getCourseDescription());

	}

}
