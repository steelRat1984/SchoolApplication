package ua.foxminded.SchoolApplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.dao.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;

class GroupServiceTest {

	@Mock
	private GroupDAO groupDAO;

	@InjectMocks
	private GroupService groupService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void returnGroup_WhenGroupIdExist() {
		Group expectedGroup = new Group(1, "groupName");
		when(groupDAO.getGroupById(1)).thenReturn(expectedGroup);
		Group actualGroup = groupService.getGroupById(1);
		assertEquals(expectedGroup, actualGroup);
		verify(groupDAO).getGroupById(1);
	}

	@Test
	public void returnGroupsByNumberOfStudents() {
		Group group1 = new Group(1, "Group1");
		Group group2 = new Group(2, "Group2");
		List<Group> expectedGroups = Arrays.asList(group1, group2);
		when(groupDAO.getGroupByNumberOfStudents(10)).thenReturn(expectedGroups);
		List<Group> actualGroups = groupService.getGroupByNumberOfStudents(10);

		assertEquals(expectedGroups, actualGroups);
	}

	@Test
	public void shouldDelegateGroupCreationToGroupDAO() {
		Group group = new Group();
		groupService.createGroup(group);
		verify(groupDAO).createGroup(group);
	}

}
