package ua.foxminded.SchoolApplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.dao.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.service.GroupService;

class GroupServiсeTest {

	@Mock
	private GroupDAO groupDAO;

	@InjectMocks
	private GroupService groupService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void returnGroup_IfGroupIdIsExisting() {
		Group expectedGroup = new Group(1, "groupName");
		when(groupDAO.getGroupById(1)).thenReturn(expectedGroup);
		Group actualGroup = groupService.getGroupById(1);
		assertEquals(expectedGroup, actualGroup);
		verify(groupDAO).getGroupById(1);
	}

	@Test
	public void buildGroupReport_ReturnsCorrectMap() {
		Group group1 = new Group(1, "group1", Arrays.asList(new Student(), new Student()));
		Group group2 = new Group(1, "group2", Arrays.asList(new Student()));
		Group group3 = new Group(1, "group3", Arrays.asList(new Student(), new Student(), new Student()));
		Group group4 = new Group(1, "group4", Arrays.asList(new Student(), new Student(), new Student()));
		List<Group> groups = Arrays.asList(group1, group2, group3, group4);
		when(groupDAO.getAllGroups()).thenReturn(groups);
		Map<Integer, List<Group>> actualReport = groupService.buildGroupReport();

		assertEquals(3, actualReport.size());
		assertEquals(Arrays.asList(group2), actualReport.get(1));
		assertEquals(Arrays.asList(group1), actualReport.get(2));
		assertEquals(Arrays.asList(group3, group4), actualReport.get(3));
	}
	
	@Test
	public void createGroup_CallsGroupDAO() {
		Group group = new Group();
		groupService.createGroup(group);
		verify(groupDAO).createGroup(group);
	}
}
