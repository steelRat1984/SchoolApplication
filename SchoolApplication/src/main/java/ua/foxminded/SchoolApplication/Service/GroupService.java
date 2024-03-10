package ua.foxminded.SchoolApplication.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.database.GroupDAO;
import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class GroupService {
	private GroupDAO groupDAO = new GroupDAO();
	
	public List<Group> getAllGrouops() {
		List<Group> allGroups = groupDAO.selectAllGroups();
		return allGroups;
	}

	public Map<Integer, List<Group>> getDataforReport() {
		Map<Integer, List<Group>> groupsByNumberOfStudents = new HashMap<>();
		List<Group> groups = getAllGrouops();
		Collections.sort(groups,
				(group1, group2) -> Integer.compare(group1.getNumberOfStudents(), group2.getNumberOfStudents()));
		for (Group group : groups) {
			groupsByNumberOfStudents.computeIfAbsent(group.getNumberOfStudents(), k -> new ArrayList<>()).add(group);
		}
		return groupsByNumberOfStudents;
	}

}
