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

import ua.foxminded.SchoolApplication.model.Student;

class StudentMapperTest {
	@Mock
	private ResultSet rs;

	@Mock
	private GroupMapper groupMapper;

	@InjectMocks
	private StudentMapper studentMapper;

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);
		when(rs.getInt("student_id")).thenReturn(1);
		when(rs.getString("first_name")).thenReturn("John");
		when(rs.getString("last_name")).thenReturn("Doe");
		when(rs.getString("group_name")).thenReturn("Group A");
		when(rs.getInt("group_id")).thenReturn(1);
	}

	@Test
	public void shouldNotReturnNull() throws SQLException {
		Student student = studentMapper.mapRow(rs, 1);
		assertNotNull(student);
		assertNotNull(student.getGroup());
		assertNotNull(student.getCourses());
	}

	@Test
	public void shouldReturnAllStudentFields() throws SQLException {
		Student student = studentMapper.mapRow(rs, 1);
		assertEquals(1, student.getStudentID());
		assertEquals("John", student.getFirstName());
		assertEquals("Doe", student.getLastName());
		assertEquals(1, student.getGroup().getGroupID());
		assertEquals("Group A", student.getGroup().getGroupName());
	}

}
