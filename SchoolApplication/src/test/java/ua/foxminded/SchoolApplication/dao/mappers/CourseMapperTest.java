package ua.foxminded.SchoolApplication.dao.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.model.Course;

class CourseMapperTest {
	@Mock
	private ResultSet rs;

	@InjectMocks
	private CourseMapper courseMaper;

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);
		when(rs.getInt("course_id")).thenReturn(1);
		when(rs.getString("course_name")).thenReturn("Math");
		when(rs.getString("course_description")).thenReturn("Math Course");
	}

	@Test
	public void shoulReturnGroup() throws SQLException {
		Course course = courseMaper.mapRow(rs, 1);
		assertNotNull(course);
		assertEquals(1, course.getCourseID());
		assertEquals("Math", course.getCourseName());
		assertEquals("Math Course", course.getCourseDescription());
	}
}
