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

import ua.foxminded.SchoolApplication.DAO.Database;
import ua.foxminded.SchoolApplication.DAO.GroupDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;

class GroupDAOTest {
	@Mock
	private Connection connectionMock;

	@Mock
	private PreparedStatement preparedStatementMock;

	@Mock
	private ResultSet resultSetMock;

	private GroupDAO groupDAO;
	private MockedStatic<Database> databaseMock;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		databaseMock = Mockito.mockStatic(Database.class);
	 	databaseMock.when(Database::connection).thenReturn(connectionMock);	
		
		when (connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when (preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		groupDAO = new GroupDAO();
	}

	@AfterEach
	public void tearDown() {
		databaseMock.close();
	}

	@Test
	public void testGetGroupByID() throws SQLException {
		int groupId = 1;
		String groupName = "TestName";
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getInt("group_id")).thenReturn(groupId);
		when(resultSetMock.getString("group_name")).thenReturn(groupName);
		Group groupResult = groupDAO.getGroupById(groupId);
		assertEquals(groupId, groupResult.getGroupID());
		assertEquals(groupName, groupResult.getGroupName());
		
	}

	@Test
	public void testGetListActualGroup() throws SQLException {
		String name1 = "Name1";
		String name2 = "Name2";
		int groupId1 = 1;
		int groupId2 = 2;

		when(resultSetMock.next()).thenReturn(true, true, false);
		when(resultSetMock.getInt("group_id")).thenReturn(groupId1, groupId2);
		when(resultSetMock.getString("group_name")).thenReturn(name1, name2);
		List<Group> resultGroupsList = groupDAO.selectAllGroups();

		assertEquals(2, resultGroupsList.size());
		assertEquals(groupId1, resultGroupsList.get(0).getGroupID());
		assertEquals(groupId2, resultGroupsList.get(1).getGroupID());
		assertEquals(name1, resultGroupsList.get(0).getGroupName());
		assertEquals(name2, resultGroupsList.get(1).getGroupName());
	
	}

}
