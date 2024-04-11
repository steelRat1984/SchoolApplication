package ua.foxminded.SchoolApplication.service;

import java.util.List;

import ua.foxminded.SchoolApplication.dao.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;

public class GroupService {
	private GroupDAO groupDAO = new GroupDAO();

	public Group getGroupById(int groupId) {
		return groupDAO.getGroupById(groupId);
	}

	public List<Group> getGroupByNumberOfStudents (int numberOfStudents){
		return groupDAO.getGroupByNumberOfStudents(numberOfStudents);
	}
	
	public void createGroup(Group group) {
		groupDAO.createGroup(group);
	}
}
