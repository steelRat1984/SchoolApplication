package ua.foxminded.SchoolApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.SchoolApplication.dao.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;

@Service
public class GroupService {
	private final GroupDAO groupDAO;
	
	@Autowired
	public GroupService(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

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
