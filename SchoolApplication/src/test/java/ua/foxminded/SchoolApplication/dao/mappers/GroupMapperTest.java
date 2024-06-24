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

import ua.foxminded.SchoolApplication.model.Group;

class GroupMapperTest {

	@Mock
	private ResultSet rs;

	@InjectMocks
	private GroupMapper groupMapper;

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);
		when(rs.getInt("group_id")).thenReturn(1);
		when(rs.getString("group_name")).thenReturn("Group A");
	}

	@Test
	public void shoulReturnGroup() throws SQLException {
		Group group = groupMapper.mapRow(rs, 1);
		assertNotNull(group);
		assertEquals(1, group.getGroupID());
		assertEquals("Group A", group.getGroupName());
	}
}
