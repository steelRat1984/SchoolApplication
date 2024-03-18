package ua.foxminded.SchoolApplication.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.DAO.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;


public class GroupService {
	private GroupDAO groupDAO = new GroupDAO();
	
	public Group getGroupById (int groupId) {
		return groupDAO.getGroupById(groupId);
	}

	public Map<Integer, List<Group>> buildGroupReport() {
		Map<Integer, List<Group>> groupsByNumberOfStudents = new HashMap<>();
		List<Group> groups = groupDAO.getAllGroups();
		for (Group group : groups) {
			groupsByNumberOfStudents.computeIfAbsent(group.getStudentsInGroup().size(), k -> new ArrayList<>()).add(group);
		}
		return groupsByNumberOfStudents;
	}

}
